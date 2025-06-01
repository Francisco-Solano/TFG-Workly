import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
//import Header from '../components/Header';
import { useAuth } from '../context/AuthContext';
import CardColumn from '../components/CardColumn';
import TaskDetailModal from '../components/TaskDetailModal';

function isoToDateInput(isoStr: string): string {
  if (!isoStr) return "";
  return isoStr.slice(0, 10); // Convierte "2025-05-12T00:00:00.000+00:00" => "2025-05-12"
}

interface Project {
  id: number;
  title: string;
  favorite: boolean;
  owner?: boolean;
}

interface Collaborator {
  id: number;            // el backend te está enviando “id”
  username: string;      // backend te envía “username”
  email: string;         // backend te envía “email”
  foto?: string;         // opcional, si luego quisieras mostrar foto
  rol?: string;          // opcional, si necesitas el rol
}


interface Subtask {
  id: number;
  title: string;
  completed: boolean;
  assignee?: string;
}

interface Task {
  id: number;
  title: string;
  description?: string;
  dueDate?: string;
  completed?: boolean;
  assignedUserId?: number;     // <--- usuario asignado
  assignedUserEmail?: string;  // <--- para mostrar el email en el modal
  // Si quieres dejar `members` para futuras mejoras, OK, pero aquí usaremos solo assignedUserId/email
  //labels?: string[];
  subtasks?: Subtask[];
}


interface Card {
  id: number;
  title: string;
  tasks: Task[];
}

interface ProjectDetailProps {
  projectId: number;
  onBack: () => void;
}

const Projects: React.FC<ProjectDetailProps> = ({ projectId }) => {
  const { user } = useAuth();
  const token = user?.token;

  const [project, setProject] = useState<Project | null>(null);
  // ⬇️ Colaboradores
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);

  // ⬆️ Colaboradores
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [cards, setCards] = useState<Card[]>([]);
  const [newCardTitle, setNewCardTitle] = useState('');
  const [creatingCard, setCreatingCard] = useState(false);

  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);

  const [draggedCardIndex, setDraggedCardIndex] = useState<number | null>(null);
  const [draggingTask, setDraggingTask] = useState<{ taskId: number; fromCardId: number } | null>(null);

  // Reordenar columnas
  const reorderCards = (list: Card[], from: number, to: number) => {
    const result = [...list];
    const [moved] = result.splice(from, 1);
    result.splice(to, 0, moved);
    return result;
  };

  const handleCardDragStart = (index: number) => setDraggedCardIndex(index);
  const handleCardDragEnd   = () => setDraggedCardIndex(null);

  const handleCardDrop = async (index: number) => {
    if (draggedCardIndex === null || draggedCardIndex === index) return;
    const updated = reorderCards(cards, draggedCardIndex, index);
    setCards(updated);
    setDraggedCardIndex(null);
    try {
      for (let i = 0; i < updated.length; i++) {
        await fetch(
          `http://localhost:8080/api/v1/tablas/${updated[i].id}/posicion?posicion=${i}`,
          {
            method: 'PATCH',
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${token}`,
            },
          }
        );
      }
    } catch (err) {
      console.error('Error actualizando posiciones:', err);
    }
  };

  // Drag & drop tareas
  const handleTaskDragStart = (taskId: number, fromCardId: number) => {
    setDraggingTask({ taskId, fromCardId });
  };
  const handleTaskDragEnd = () => setDraggingTask(null);

const handleTaskDrop = async (
  toCardId: number,
  toIndex: number | null,
  taskId: number,
  fromCardId: number
) => {
  setCards(prev => {
    // Encuentra la tarea de manera segura
    const fromCard = prev.find(c => c.id === fromCardId);
    const taskToMove = fromCard?.tasks.find(t => t.id === taskId);

    if (!taskToMove) {
      setDraggingTask(null);
      return prev;
    }

    // Elimina la tarea de la columna origen
    const updated = prev.map(c => {
      if (c.id === fromCardId) {
        return { ...c, tasks: c.tasks.filter(t => t.id !== taskId) };
      }
      return c;
    });

    // Inserta la tarea en la columna destino en la posición correcta
    return updated.map(c => {
      if (c.id === toCardId) {
        // Muy importante: solo insertamos si no está ya
        if (!c.tasks.find(t => t.id === taskId)) {
          const newTasks = [...c.tasks];
          newTasks.splice(toIndex ?? newTasks.length, 0, taskToMove);
          return { ...c, tasks: newTasks };
        }
      }
      return c;
    });
  });

  setDraggingTask(null);

  // BACKEND
  if (fromCardId !== toCardId) {
    await fetch(`http://localhost:8080/api/v1/tareas/${taskId}/mover/${toCardId}`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  const realPosition = toIndex ?? 0;
  await fetch(
    `http://localhost:8080/api/v1/tareas/${taskId}/posicion?posicion=${realPosition}`,
    {
      method: 'PATCH',
      headers: { Authorization: `Bearer ${token}` },
    }
  );
};




  const handleReorderTasks = (cardId: number, newTasksOrder: Task[]) => {
    setCards(prev =>
      prev.map(card =>
        card.id === cardId ? { ...card, tasks: newTasksOrder } : card
      )
    );
    setDraggingTask(null);
  };

  // Crear columna
  const handleAddCard = async () => {
    if (!newCardTitle.trim()) return;
    try {
      const res = await fetch('http://localhost:8080/api/v1/tablas/crear', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          nombre: newCardTitle.trim(),
          idProyecto: projectId,
        }),
      });
      if (!res.ok) throw new Error();
      const tab = await res.json();
      setCards(prev => [
        ...prev,
        { id: tab.tablaId, title: tab.nombre, tasks: [] },
      ]);
      setNewCardTitle('');
      setCreatingCard(false);
    } catch (err) {
      console.error('Error al crear tabla:', err);
      alert('No se pudo crear la tabla');
    }
  };

  // Crear tarea
  const handleAddTask = async (cardId: number, title: string) => {
    const tokenRaw = localStorage.getItem('user');
    const tok = tokenRaw ? JSON.parse(tokenRaw).token : null;
    if (!tok) return console.error('Token no encontrado');
    try {
      const res = await fetch('http://localhost:8080/api/v1/tareas/crear', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${tok}`,
        },
        body: JSON.stringify({
          titulo: title,
          estado: 'Pendiente',
          tablaId: cardId,
        }),
      });
      if (!res.ok) throw new Error();
      const tarea = await res.json();
      const newTask: Task = {
        id: tarea.tareaId,
        title: tarea.titulo,
        description: tarea.descripcion || '',
        dueDate: tarea.fechaLimite || '',
        completed: tarea.estado === 'Completada',
      //  members: tarea.miembros || [],
    //    labels: tarea.etiquetas?.map((e: any) => e.nombre) || [],
        subtasks: tarea.subtareas || [],
      };
      setCards(prev =>
        prev.map(c =>
          c.id === cardId
            ? { ...c, tasks: [...c.tasks, newTask] }
            : c
        )
      );
    } catch (err) {
      console.error('Error al crear tarea:', err);
    }
  };

  // Editar columna
  const handleUpdateCardTitle = async (cardId: number, newTitle: string) => {
    try {
      const res = await fetch(`http://localhost:8080/api/v1/tablas/${cardId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ nombre: newTitle, idProyecto: projectId }),
      });
      if (!res.ok) throw new Error();
      const updated = await res.json();
      setCards(prev =>
        prev.map(c =>
          c.id === cardId ? { ...c, title: updated.nombre } : c
        )
      );
    } catch (err) {
      console.error('Error actualizando columna:', err);
    }
  };

  const handleDeleteCard = async (cardId: number) => {
    if (!window.confirm('¿Eliminar esta tabla?')) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/v1/tablas/${cardId}`,
        { method: 'DELETE', headers: { Authorization: `Bearer ${token}` } }
      );
      if (!res.ok) throw new Error();
      setCards(prev => prev.filter(c => c.id !== cardId));
    } catch (err) {
      console.error('Error al eliminar tabla:', err);
    }
  };

  // Abrir modal de tarea
/**
 * Al pulsar sobre una tarea, este método:
 * 1️⃣ Verifica que el ID de la tarea sea válido.
 * 2️⃣ Extrae el token de autenticación del localStorage.
 * 3️⃣ Hace un GET a `/api/v1/tareas/{task.id}` para obtener todos los detalles de la tarea,
 *    incluido el listado de subtareas.
 * 4️⃣ Convierte el campo `fechaLimite` (ISO) a formato `YYYY-MM-DD` para mostrarlo en el input date.
 * 5️⃣ Inicializa el estado `selectedTask` con toda la información recibida (título, descripción,
 *    fecha límite, estado, asignación de usuario y subtareas).
 * 6️⃣ Abre el modal (pone `isTaskModalOpen` en true) para que se muestre la información.
 */
const handleOpenTaskModal = async (task: Task) => {
  // 1️⃣ Asegurarnos de que task.id sea un número válido
  if (typeof task.id !== 'number') {
    console.error('ID de tarea inválido:', task);
    return;
  }

  // 2️⃣ Extraer token de autenticación desde localStorage
  const tokenRaw = localStorage.getItem('user');
  const tok = tokenRaw ? JSON.parse(tokenRaw).token : null;
  if (!tok) {
    console.error('Token no encontrado');
    return;
  }

  try {
    // 3️⃣ Hacer petición al backend para obtener todos los datos de la tarea
    const res = await fetch(`http://localhost:8080/api/v1/tareas/${task.id}`, {
      headers: { Authorization: `Bearer ${tok}` },
    });
    // Si la respuesta no es OK, lanzamos un error para caer en el catch
    if (!res.ok) throw new Error(`Error ${res.status} al cargar tarea`);

    // Parsear la respuesta JSON
    const full = await res.json();

    // 4️⃣ Convertir `fechaLimite` en formato ISO ("YYYY-MM-DDT...") a "YYYY-MM-DD" para el input date
    const fechaConvertida = full.fechaLimite ? isoToDateInput(full.fechaLimite) : '';

    // 5️⃣ Mapear la respuesta completada (`full`) a nuestro estado local `selectedTask`.
    //    - id: el propio ID que recibimos en full.tareaId
    //    - title: full.titulo
    //    - description: full.descripcion (si viene vacía, ponemos '')
    //    - dueDate: la fecha convertida (o cadena vacía si no existe)
    //    - completed: true si full.estado === "Completada", false en caso contrario
    //    - assignedUserId / assignedUserEmail: si el backend devuelve un objeto `asignado`, lo usamos; 
    //      en otro caso, undefined
    //    - subtasks: mapear cada elemento de full.subtareas a la forma { id, title, completed }
    setSelectedTask({
      id: full.tareaId,
      title: full.titulo,
      description: full.descripcion || '',
      dueDate: fechaConvertida,
      completed: full.estado === 'Completada',
      assignedUserId: full.asignado?.id ?? undefined,
      assignedUserEmail: full.asignado?.email ?? undefined,
      subtasks: (full.subtareas || []).map((s: any) => ({
        id: s.subtareaId,
        title: s.titulo,
        completed: s.estado === 'Completada',
        assignee: undefined,
      })),
    });

    // 6️⃣ Abrir el modal
    setIsTaskModalOpen(true);
  } catch (err) {
    console.error('Error cargando detalles:', err);
  }
};





  const handleCloseTaskModal = () => {
    setSelectedTask(null);
    setIsTaskModalOpen(false);
  };

  const handleUpdateTask = (updated: Task) => {
    setCards(prev =>
      prev.map(c => ({
        ...c,
        tasks: c.tasks.map(t => (t.id === updated.id ? updated : t)),
      }))
    );
  };

  const handleDeleteTask = (taskId: number) => {
    setCards(prev =>
      prev.map(c => ({
        ...c,
        tasks: c.tasks.filter(t => t.id !== taskId),
      }))
    );
    setIsTaskModalOpen(false);
  };

  const fetchCollaborators = async () => {
    if (!token) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/v1/proyectos/${projectId}/colaboradores`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      if (!res.ok) throw new Error('Error listando colaboradores');
      const data: Collaborator[] = await res.json();

      setCollaborators(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchCollaborators();
  }, [projectId, token]);

  // ────────────────────────────────────────────────────────────
  // Añadir colaborador
  // ────────────────────────────────────────────────────────────
  const handleAddCollaborator = async () => {
  if (!token) return alert('No autenticado');
  const email = prompt('Email del colaborador:')?.trim();
  if (!email) return;

  try {
    // 1) Buscar usuario por email
    const userRes = await fetch(
      `http://localhost:8080/api/v1/usuarios/email/${encodeURIComponent(email)}`,
      { headers: { Authorization: `Bearer ${token}` } }
    );
    if (!userRes.ok) throw new Error('Usuario no encontrado');
    const usr = (await userRes.json()) as { id: number; username: string; email: string };

    // 2) Si ya existía en el state, no hagas POST y muestra un aviso
    if (collaborators.some((c) => c.id === usr.id)) {
      return alert('Este usuario ya es colaborador del proyecto.');
    }

    // 3) Añadir como colaborador
    const linkRes = await fetch(
      `http://localhost:8080/api/v1/proyectos/${projectId}/colaboradores?usuarioId=${usr.id}&rol=ROLE_USER`,
      {
        method: 'POST',
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    if (!linkRes.ok) throw new Error('Error al añadir colaborador');

    alert(`Colaborador ${usr.username} agregado`);
    await fetchCollaborators();
  } catch (err: any) {
    console.error(err);
    alert(err.message || 'Error al añadir colaborador');
  }
};


  // ────────────────────────────────────────────────────────────
  // Eliminar colaborador
  // ────────────────────────────────────────────────────────────
  const handleRemoveCollaborator = async (userId: number) => {
    if (!token) return;
    if (!confirm('Eliminar este colaborador?')) return;

    try {
      console.log(`[DEBUG] Eliminando colaborador ID=${userId}`);
      const res = await fetch(
        `http://localhost:8080/api/v1/proyectos/${projectId}/colaboradores/${userId}`,
        {
          method: 'DELETE',
          headers: { Authorization: `Bearer ${token}` }
        }
      );
      console.log('[DEBUG] Respuesta DELETE:', res.status, res.statusText);
      if (res.status === 204) {
        await fetchCollaborators();
      } else if (res.status === 404) {
        alert('Colaborador no encontrado en este proyecto.');
        await fetchCollaborators();
      } else {
        throw new Error(`Error al eliminar colaborador: status=${res.status}`);
      }
    } catch (error) {
      console.error(error);
      alert('No se pudo eliminar colaborador');
    }
  };




  // Carga inicial de columnas + tareas + proyecto
  useEffect(() => {
  if (!token) {
    setError('No autenticado');
    setLoading(false);
    return;
  }
  (async () => {
    try {
      setLoading(true);
      const res = await fetch(
        `http://localhost:8080/api/v1/tablas/proyecto/${projectId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      if (!res.ok) throw new Error();
      const tablas = await res.json();

      const formateadas: Card[] = tablas
        .sort((a: any, b: any) => a.posicion - b.posicion)
        .map((t: any) => ({
          id:    t.tablaId,
          title: t.nombre,
          tasks: t.tareas?.map((ta: any) => ({
            id:                ta.tareaId,
            title:             ta.titulo,
            description:       ta.descripcion || '',
            dueDate:           isoToDateInput(ta.fechaLimite),
            completed:         ta.estado === 'Completada',
        //    members:           ta.miembros || [],
            assignedUserId:    ta.asignado?.id ?? undefined,
            assignedUserEmail: ta.asignado?.email ?? undefined,
            // labels:         ta.etiquetas?.map((e: any) => e.nombre) || [],
            subtasks: (ta.subtareas || []).map((s: any) => ({
    id: s.subtareaId,
    title: s.titulo,
    completed: s.estado === 'Completada',
    assignee: undefined, // (si no usas asignaciones por ahora)
  })),
          })) || [],
        }));

      setCards(formateadas);
      setProject({
        id: projectId,
        title: `Proyecto ${projectId}`,
        favorite: false,
        owner: true,
      });
      setError(null);
    } catch (err) {
      console.error(err);
      setError('Error cargando proyecto');
    } finally {
      setLoading(false);
    }
  })();
}, [projectId, token]);


  if (loading) return <div className="text-center">Cargando proyecto...</div>;
  if (error) return <div className="text-center text-red-500">{error}</div>;
  if (!project) return <div className="text-center">Proyecto no encontrado.</div>;

  return (
    <div className="flex min-h-screen bg-gray-100 font-sans">
      <Sidebar />
      <div className="flex-1 flex flex-col">
        <main className="p-6 flex-1 overflow-auto">
          {/* Título y Compartir */}
          <div className="flex items-center justify-between mb-4">
            <h1 className="text-3xl font-bold">{project.title}</h1>
            <button
              onClick={handleAddCollaborator}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              Compartir
            </button>
          </div>

          {/* Listado de Colaboradores */}
          <div className="mb-6">
            <h2 className="font-medium">Colaboradores:</h2>
            <div className="flex flex-wrap gap-2 mt-2">
              {collaborators.map(c => (
                <div
                  key={c.id}
                  className="flex items-center bg-white p-2 rounded shadow-sm"
                >
                  <span className="mr-2">{c.email}</span>
                  <button
                    onClick={() => handleRemoveCollaborator(c.id)}
                    className="text-red-500 hover:text-red-700"
                  >
                    ×
                  </button>
                </div>
              ))}
            </div>
          </div>

          {/* Tableros Kanban */}
          <div className="flex space-x-6 overflow-x-auto pb-4 items-start">
            {cards.map((card, idx) => (
              <CardColumn
                key={card.id}
                card={card}
                index={idx}
                draggingTask={draggingTask}
                onCardDragStart={() => handleCardDragStart(idx)}
                onCardDragEnd={handleCardDragEnd}
                onCardDrop={() => handleCardDrop(idx)}
                onTaskDragStart={handleTaskDragStart}
                onTaskDragEnd={handleTaskDragEnd}
                onTaskDrop={handleTaskDrop}
                onReorderTasks={handleReorderTasks}
                onAddTask={handleAddTask}
                onTaskClick={handleOpenTaskModal}
                onUpdateCardTitle={handleUpdateCardTitle}
                onDeleteCard={handleDeleteCard}
              />
            ))}

            {creatingCard ? (
              <div className="w-[320px] p-4 bg-white rounded-xl shadow-md">
                <input
                  value={newCardTitle}
                  onChange={e => setNewCardTitle(e.target.value)}
                  placeholder="Nombre de la tarjeta"
                  className="w-full mb-2 p-2 border rounded"
                />
                <div className="flex justify-between">
                  <button
                    onClick={handleAddCard}
                    className="px-4 py-2 bg-blue-600 text-white rounded"
                  >
                    Crear
                  </button>
                  <button
                    onClick={() => setCreatingCard(false)}
                    className="px-4 py-2 text-gray-600"
                  >
                    Cancelar
                  </button>
                </div>
              </div>
            ) : (
              <button
                onClick={() => setCreatingCard(true)}
                className="w-[320px] p-4 bg-gray-300 text-gray-700 rounded-xl shadow-md flex items-center justify-center"
              >
                + Añadir nueva tarjeta
              </button>
            )}
          </div>
        </main>

        {selectedTask && isTaskModalOpen && (
          <TaskDetailModal
          
            task={selectedTask}
            collaborators={collaborators}   // <-- pasa la lista de colaboradores como prop
            onClose={handleCloseTaskModal}
            onUpdateTask={handleUpdateTask}
            onDeleteTask={handleDeleteTask}
          />
        )}
      </div>
    </div>
  );
};

export default Projects;
