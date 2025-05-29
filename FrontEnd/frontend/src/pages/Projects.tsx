// Projects.tsx (Componente principal con lógica de drag & drop y prevención de errores)
import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Header from '../components/Header';
import { useAuth } from '../context/AuthContext';
import CardColumn from '../components/CardColumn';
import TaskDetailModal from '../components/TaskDetailModal';

interface Project {
  id: number;
  title: string;
  favorite: boolean;
  owner?: boolean;
}

interface Task {
  id: number;
  title: string;
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

const Projects: React.FC<ProjectDetailProps> = ({ projectId, onBack }) => {
  const { user } = useAuth();
  const token = user?.token;

  const [project, setProject] = useState<Project | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [cards, setCards] = useState<Card[]>([]);
  const [newCardTitle, setNewCardTitle] = useState('');
  const [creatingCard, setCreatingCard] = useState(false);

  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);

  const [draggedCardIndex, setDraggedCardIndex] = useState<number | null>(null);
  const [draggingTask, setDraggingTask] = useState<{ taskId: number; fromCardId: number } | null>(null);

  const reorderCards = (list: Card[], from: number, to: number) => {
    const result = [...list];
    const [moved] = result.splice(from, 1);
    result.splice(to, 0, moved);
    return result;
  };

  const handleCardDragStart = (index: number) => setDraggedCardIndex(index);
  const handleCardDragEnd = () => setDraggedCardIndex(null);


 const handleCardDrop = async (index: number) => {
  if (draggedCardIndex === null || draggedCardIndex === index) return;

  const updated = reorderCards(cards, draggedCardIndex, index);
  setCards(updated); // actualiza visualmente primero
  setDraggedCardIndex(null);

  // Actualiza la posición en el backend
  try {
    for (let i = 0; i < updated.length; i++) {
      const card = updated[i];
      await fetch(`http://localhost:8080/api/v1/tablas/${card.id}/posicion?posicion=${i}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`, // elimina si no usas auth
        },
      });
    }
  } catch (error) {
    console.error('Error actualizando posiciones:', error);
  }
};


  const handleTaskDragStart = (taskId: number, fromCardId: number) => {
    setDraggingTask({ taskId, fromCardId });
  };

  const handleTaskDragEnd = () => {
    setDraggingTask(null);
  };

  const handleTaskDrop = (
    toCardId: number,
    toIndex: number | null,
    taskId: number,
    fromCardId: number
  ) => {
    if (fromCardId === toCardId) {
      setDraggingTask(null);
      return;
    }

    setCards(prev => {
      const fromCard = prev.find(c => c.id === fromCardId);
      const toCard = prev.find(c => c.id === toCardId);
      if (!fromCard || !toCard) return prev;

      const taskToMove = fromCard.tasks.find(t => t.id === taskId);
      if (!taskToMove) return prev;

      const updatedFromTasks = fromCard.tasks.filter(t => t.id !== taskId);
      const updatedToTasks = [...toCard.tasks];
      const insertAt = toIndex !== null ? toIndex : updatedToTasks.length;
      updatedToTasks.splice(insertAt, 0, taskToMove);

      return prev.map(c => {
        if (c.id === fromCardId) return { ...c, tasks: updatedFromTasks };
        if (c.id === toCardId) return { ...c, tasks: updatedToTasks };
        return c;
      });
    });

    setDraggingTask(null);
  };

  const handleReorderTasks = (cardId: number, newTasksOrder: Task[]) => {
    setCards(prev => prev.map(card => card.id === cardId ? { ...card, tasks: newTasksOrder } : card));
    setDraggingTask(null);
  };

  const handleAddCard = async () => {
  if (!newCardTitle.trim()) return;

  try {
    const response = await fetch('http://localhost:8080/api/v1/tablas/crear', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}` // Elimina esto si no usas autenticación
      },
      body: JSON.stringify({
        nombre: newCardTitle.trim(),
        idProyecto: projectId
      })
    });

    if (!response.ok) throw new Error('Error al crear la tabla');

    const nuevaTabla = await response.json();

    const newCard: Card = {
      id: nuevaTabla.tablaId,
      title: nuevaTabla.nombre,
      tasks: [],
    };

    setCards(prev => [...prev, newCard]);
    setNewCardTitle('');
    setCreatingCard(false);
  } catch (error) {
    console.error('Error al crear tabla:', error);
    alert('No se pudo crear la tabla');
  }
};


  const handleAddTask = (cardId: number, title: string) => {
    setCards(prev => prev.map(c =>
      c.id === cardId ? { ...c, tasks: [...c.tasks, { id: Date.now(), title }] } : c
    ));
  };

 const handleUpdateCardTitle = async (cardId: number, newTitle: string) => {
  try {
    const updated = {
      nombre: newTitle,
      idProyecto: projectId
    };

    const response = await fetch(`http://localhost:8080/api/v1/tablas/${cardId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Si usas autenticación
      },
      body: JSON.stringify(updated),
    });

    if (!response.ok) throw new Error('Error al actualizar la tabla');

    const updatedCard = await response.json();

    setCards(prev =>
      prev.map(card => card.id === cardId
        ? { ...card, title: updatedCard.nombre }
        : card
      )
    );
  } catch (error) {
    console.error('Error actualizando el nombre de la tarjeta:', error);
  }
};


  const handleDeleteCard = async (cardId: number) => {
  try {
    const confirmed = window.confirm('¿Estás seguro de que deseas eliminar esta tabla?');
    if (!confirmed) return;

    const response = await fetch(`http://localhost:8080/api/v1/tablas/${cardId}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`, // quita esta línea si no usas auth
      },
    });

    if (!response.ok) throw new Error('Error al eliminar la tabla');

    // Eliminar visualmente del frontend
    setCards(prev => prev.filter(card => card.id !== cardId));
  } catch (error) {
    console.error('Error al eliminar la tabla:', error);
  }
};


  const handleOpenTaskModal = (task: Task) => {
    setSelectedTask(task);
    setIsTaskModalOpen(true);
  };

  const handleCloseTaskModal = () => {
    setSelectedTask(null);
    setIsTaskModalOpen(false);
  };

  const handleUpdateTask = (updatedTask: Task) => {
    setCards(prev => prev.map(card => ({
      ...card,
      tasks: card.tasks.map(task => task.id === updatedTask.id ? updatedTask : task)
    })));
  };

  const handleDeleteTask = (taskId: number) => {
    setCards(prev => prev.map(card => ({
      ...card,
      tasks: card.tasks.filter(task => task.id !== taskId)
    })));
    setIsTaskModalOpen(false);
  };

 useEffect(() => {
  if (!token) {
    setError("No autenticado");
    setLoading(false);
    return;
  }

  const fetchTablas = async () => {
  try {
    setLoading(true);
    const response = await fetch(`http://localhost:8080/api/v1/tablas/proyecto/${projectId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) throw new Error('Error al cargar tablas');

    const tablas: any[] = await response.json();

    // 🔥 Ordenar por posición de forma segura
    const ordenadas = tablas.sort((a: any, b: any) => a.posicion - b.posicion);

    // 🔄 Mapear a tu formato de frontend
    const formateadas: Card[] = ordenadas.map((t: any) => ({
      id: t.tablaId,
      title: t.nombre,
      tasks: t.tareas?.map((ta: any) => ({
        id: ta.id,
        title: ta.titulo,
      })) || [],
    }));

    setCards(formateadas);

    // Simula el título del proyecto (ajustar según tu modelo real)
    setProject({ id: projectId, title: `Proyecto ${projectId}`, favorite: false, owner: true });
    setError(null);
  } catch (err) {
    setError('No se pudieron cargar las tarjetas.');
    console.error(err);
  } finally {
    setLoading(false);
  }
};


  fetchTablas();
}, [projectId, token]);


  if (loading) return <div className="text-center">Cargando proyecto...</div>;
  if (error) return <div className="text-center text-red-500">{error}</div>;
  if (!project) return <div className="text-center">Proyecto no encontrado.</div>;

  return (
    <div className="flex min-h-screen bg-gray-100 font-sans">
      <Sidebar />
      <div className="flex-1 flex flex-col">
        <Header />
        <main className="p-6 flex-1 overflow-auto">
          <h1 className="text-3xl font-bold mb-4">{project.title}</h1>
          <div className="flex flex-nowrap space-x-6 overflow-x-auto pb-4 items-start">
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
                  <button onClick={handleAddCard} className="px-4 py-2 bg-blue-600 text-white rounded">Crear</button>
                  <button onClick={() => setCreatingCard(false)} className="px-4 py-2 text-gray-600">Cancelar</button>
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