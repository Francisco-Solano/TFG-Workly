import React, { useState } from 'react';

interface TaskDetailModalProps {
  task: {
    id: number;
    title: string;
    description?: string;
    dueDate?: string;
    completed?: boolean;
    members?: string[];
    labels?: string[];
  };
  onClose: () => void;
  onUpdateTask: (updatedTask: TaskDetailModalProps['task']) => void;
  onDeleteTask: (taskId: number) => void;
}

interface Subtask {
  id: number;
  title: string;
  completed: boolean;
  assignee?: string;
}


const TaskDetailModal: React.FC<TaskDetailModalProps> = ({ task, onClose, onUpdateTask, onDeleteTask }) => {
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description || '');
  const [isCompleted, setIsCompleted] = useState(task.completed || false);
  const [dueDate, setDueDate] = useState(task.dueDate || '');
  const [members, setMembers] = useState(task.members || []);
  const [labels, setLabels] = useState(task.labels || []);
  const [newMemberInput, setNewMemberInput] = useState('');
  const [subtasks, setSubtasks] = useState<Subtask[]>(task.subtasks || []);


  const handleSaveDescription = () => {
    onUpdateTask({ ...task, title, description });
  };

  const handleToggleCompleted = () => {
    setIsCompleted(!isCompleted);
    onUpdateTask({ ...task, title, completed: !isCompleted });
  };

  const handleDueDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDueDate(e.target.value);
    onUpdateTask({ ...task, dueDate: e.target.value });
  };

  const handleAddMember = (member: string) => {
    if (member.trim() && !members.includes(member.trim())) {
      const updated = [...members, member.trim()];
      setMembers(updated);
      onUpdateTask({ ...task, members: updated });
    }
  };

  const handleRemoveMember = (m: string) => {
    const updated = members.filter(member => member !== m);
    setMembers(updated);
    onUpdateTask({ ...task, members: updated });
  };

  const handleAddLabel = (label: string) => {
    if (label.trim() && !labels.includes(label.trim())) {
      const updated = [...labels, label.trim()];
      setLabels(updated);
      onUpdateTask({ ...task, labels: updated });
    }
  };

  const handleRemoveLabel = (l: string) => {
    const updated = labels.filter(label => label !== l);
    setLabels(updated);
    onUpdateTask({ ...task, labels: updated });
  };

  const handleDeleteTask = () => {
    if (window.confirm(`¿Estás seguro que quieres eliminar la tarea "${task.title}"?`)) {
      onDeleteTask(task.id);
      onClose();
    }
  }

  const handleAddSubtask = () => {
  const newTitle = prompt('Título de la nueva subtarea:');
  if (newTitle && newTitle.trim()) {
    const newSubtask: Subtask = {
      id: Date.now(),
      title: newTitle.trim(),
      completed: false,
    };
    const updated = [...subtasks, newSubtask];
    setSubtasks(updated);
    onUpdateTask({ ...task, subtasks: updated });
  }
};

const handleToggleSubtask = (id: number) => {
  const updated = subtasks.map(st => st.id === id ? { ...st, completed: !st.completed } : st);
  setSubtasks(updated);
  onUpdateTask({ ...task, subtasks: updated });
};


const handleAssignSubtask = (id: number) => {
  const assignee = prompt('Asignar miembro a la subtarea:');
  if (assignee) {
    const updated = subtasks.map(st => st.id === id ? { ...st, assignee } : st);
    setSubtasks(updated);
    onUpdateTask({ ...task, subtasks: updated });
  }
};



 const saveTaskChanges = async () => {
  if (!task.id) {
    console.error("El ID de la tarea es undefined");
    return;
  }

  const tokenRaw = localStorage.getItem("user");
  const token = tokenRaw ? JSON.parse(tokenRaw)?.token : null;

  if (!token) {
    console.error("Token no encontrado");
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/api/v1/tareas/${task.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        titulo: title,
        descripcion: description,
        fechaLimite: dueDate || null,
        estado: isCompleted ? "Completa" : "Pendiente",
        etiquetaIds: [],
        prioridad: null,
        tablaId: null,
      }),
    });

    if (!response.ok) throw new Error("Error al actualizar la tarea");

    const tareaActualizada = await response.json();

    onUpdateTask({
      ...task,
      ...tareaActualizada,
    });
  } catch (err) {
    console.error("Error actualizando tarea:", err);
  }
};






  return (
  <div className="fixed inset-0 z-50 flex items-center justify-center p-6">
    <div className="bg-white rounded-lg shadow-2xl w-full max-w-3xl overflow-auto max-h-[90vh] p-6 border border-gray-200">
      <div className="flex justify-between items-start mb-6">
        <h2 className="text-2xl font-semibold text-gray-800 flex items-center">
          <input
            type="checkbox"
            checked={isCompleted}
            onChange={handleToggleCompleted}
            className="form-checkbox h-5 w-5 text-blue-600 mr-3"
          />
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            onBlur={handleSaveDescription}
            className={`text-2xl font-semibold bg-transparent border-none focus:outline-none ${
              isCompleted ? 'line-through text-gray-400' : 'text-gray-800'
            }`}
          />
        </h2>
        <button
          onClick={onClose}
          className="text-gray-400 hover:text-gray-600 text-3xl font-semibold"
        >
          &times;
        </button>
      </div>

      <div className="space-y-6 text-sm text-gray-600">
        <div className="flex flex-wrap gap-4 items-center">
          {/* Fecha */}
          <div>
            <span className="font-medium">Vencimiento:</span>
            <input
              type="date"
              value={dueDate}
              onChange={handleDueDateChange}
              className="ml-2 px-2 py-1 border border-gray-300 rounded focus:ring-2 focus:ring-blue-400"
            />
            {dueDate && (
              <span
                className={`ml-3 px-2 py-1 rounded-full text-xs font-semibold ${
                  isCompleted ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'
                }`}
              >
                {isCompleted ? 'Completada' : 'Pendiente'}
              </span>
            )}
          </div>

          {/* Miembros */}
          <div className="flex items-center gap-2">
            <span className="font-medium">Miembros:</span>
            <div className="flex gap-1">
              {members.map((m, i) => (
                <div
                  key={i}
                  className="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center text-white text-sm relative"
                >
                  {m.charAt(0).toUpperCase()}
                  <button
                    onClick={() => handleRemoveMember(m)}
                    className="absolute -top-1 -right-1 bg-white text-red-500 rounded-full text-xs w-4 h-4 flex items-center justify-center border border-red-300"
                  >
                    ×
                  </button>
                </div>
              ))}
            </div>
            <button
              onClick={() => {
                const name = prompt('Nombre del nuevo miembro:');
                if (name) handleAddMember(name);
              }}
              className="px-2 py-1 text-sm bg-gray-100 rounded hover:bg-gray-200"
            >
              + Añadir
            </button>
          </div>

          {/* Etiquetas */}
          <div className="flex items-center gap-2">
            <span className="font-medium">Etiquetas:</span>
            <div className="flex gap-2 flex-wrap">
              {labels.map((l, i) => (
                <div
                  key={i}
                  className="bg-green-200 text-green-800 px-2 py-1 rounded-full text-xs flex items-center"
                >
                  {l}
                  <button
                    onClick={() => handleRemoveLabel(l)}
                    className="ml-1 text-red-500 font-bold"
                  >
                    ×
                  </button>
                </div>
              ))}
            </div>
            <button
              onClick={() => {
                const label = prompt('Nombre de la nueva etiqueta:');
                if (label) handleAddLabel(label);
              }}
              className="px-2 py-1 text-sm bg-gray-100 rounded hover:bg-gray-200"
            >
              + Añadir
            </button>
          </div>
        </div>

        {/* Descripción */}
        <div>
          <h3 className="text-lg font-medium mb-1">Descripción</h3>
          <textarea
            className="w-full p-3 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-400 resize-none min-h-[100px]"
            placeholder="Añade una descripción..."
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            onBlur={handleSaveDescription}
          />
        </div>

        {/* Subtareas */}
        <div>
          <h3 className="text-lg font-medium mb-2">Subtareas</h3>

          {subtasks.map((subtask) => (
            <div
              key={subtask.id}
              className="bg-gray-100 p-3 rounded flex items-center justify-between"
            >
              <div className="flex items-center">
                <input
                  type="checkbox"
                  checked={subtask.completed}
                  onChange={() => handleToggleSubtask(subtask.id)}
                  className="mr-3 h-4 w-4 text-blue-500"
                />
                <span
                  className={subtask.completed ? 'line-through text-gray-400' : ''}
                >
                  {subtask.title}
                </span>
              </div>
              <div className="flex space-x-3 text-gray-400 items-center">
                {subtask.assignee && (
                  <div className="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center text-white text-xs">
                    {subtask.assignee.charAt(0).toUpperCase()}
                  </div>
                )}
                <button title="Asignar" onClick={() => handleAssignSubtask(subtask.id)}>
                  <svg className="w-5 h-5" stroke="currentColor" fill="none" viewBox="0 0 24 24">
                    <path
                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                      strokeWidth={2}
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                </button>
              </div>
            </div>
          ))}

          {/* ✅ Botón crear subtarea (fuera del .map) */}
          <button
            onClick={handleAddSubtask}
            className="mt-3 text-blue-500 hover:underline text-sm"
          >
            + Crear subtarea
          </button>
        </div>

        {/* Botón Eliminar */}
        <button
          onClick={handleDeleteTask}
          className="mt-4 w-full flex items-center justify-center gap-2 bg-red-600 hover:bg-red-700 text-white py-2 rounded shadow"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={2}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5-4h4m-4 0a1 1 0 00-1 1v1h6V4a1 1 0 00-1-1m-4 0h4"
            />
          </svg>
          Eliminar
        </button>

        {/* Botón Guardar */}
        <button
          onClick={() => {
            saveTaskChanges();
            onClose();
          }}
          className="mt-6 w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded shadow"
        >
          Guardar todos los cambios
        </button>
      </div>
    </div>
  </div>
);

};

export default TaskDetailModal;
