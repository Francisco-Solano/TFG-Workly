import React, { useState } from 'react';
import { Card, Task } from '../types/kanban';
import TaskItem from './TaskItem';

interface Props {
  card: Card;
  index: number;
  draggingTask: { taskId: number; fromCardId: number } | null;
  onCardDragStart: () => void;
  onCardDragEnd: () => void;
  onCardDrop: () => void;
  onTaskDragStart: (taskId: number, fromCardId: number) => void;
  onTaskDragEnd: () => void;
  onTaskDrop: (
    toCardId: number,
    toIndex: number | null,
    taskId: number,
    fromCardId: number
  ) => void;
  onReorderTasks: (cardId: number, newTasksOrder: Task[]) => void;
  onAddTask: (cardId: number, title: string) => void;
  onTaskClick: (task: Task) => void;
  onUpdateCardTitle: (cardId: number, title: string) => void;
  onDeleteCard: (cardId: number) => void;
}

const CardColumn: React.FC<Props> = ({
  card,
  index,
  draggingTask,
  onCardDragStart,
  onCardDragEnd,
  onCardDrop,
  onTaskDragStart,
  onTaskDragEnd,
  onTaskDrop,
  onReorderTasks,
  onAddTask,
  onTaskClick,
  onUpdateCardTitle,
  onDeleteCard,
}) => {
  const [showTaskInput, setShowTaskInput] = useState(false);
  const [newTaskTitle, setNewTaskTitle] = useState('');
  const [editingTitle, setEditingTitle] = useState(false);
  const [tempTitle, setTempTitle] = useState(card.title);
  const [draggedTaskIndex, setDraggedTaskIndex] = useState<number | null>(null);
  const [showOptions, setShowOptions] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);

  const reorderTasks = (list: Task[], from: number, to: number): Task[] => {
    const result = [...list];
    const [moved] = result.splice(from, 1);
    result.splice(to, 0, moved);
    return result;
  };

  const handleInternalDrop = (toIndex: number | null) => {
    if (draggedTaskIndex !== null) {
      const newOrder = reorderTasks(
        card.tasks,
        draggedTaskIndex,
        toIndex ?? card.tasks.length
      );
      onReorderTasks(card.id, newOrder);
      setDraggedTaskIndex(null);
      onTaskDragEnd();
    } else if (draggingTask) {
      onTaskDrop(
        card.id,
        toIndex,
        draggingTask.taskId,
        draggingTask.fromCardId
      );
    }
  };

  const handleAdd = () => {
    if (!newTaskTitle.trim()) return;
    onAddTask(card.id, newTaskTitle.trim());
    setNewTaskTitle('');
    setShowTaskInput(false);
  };

  return (
    <div
      className="relative w-[320px] bg-gray-100 rounded-xl shadow-md p-4"
      draggable={draggingTask === null}
      onMouseEnter={() => setShowOptions(true)}
      onMouseLeave={() => {
        setShowOptions(false);
        setEditingTitle(false);
        setTempTitle(card.title);
      }}
      onDragStart={e => {
        e.stopPropagation();
        onCardDragStart();
      }}
      onDragOver={e => {
        e.preventDefault();
      }}
      onDrop={e => {
        e.stopPropagation();
        onCardDrop();
      }}
      onDragEnd={e => {
        e.stopPropagation();
        onCardDragEnd();
      }}
    >
      {showOptions && (
        <div className="absolute top-2 right-2 z-20">
          <button
            onClick={() => setShowDropdown(prev => !prev)}
            className="text-gray-500 hover:text-gray-700 text-xl"
          >
            ⋮
          </button>
          {showDropdown && (
            <div className="mt-2 bg-white border border-gray-200 shadow-md rounded absolute right-0 w-28 z-30">
              <button
                onClick={() => {
                  setEditingTitle(true);
                  setShowDropdown(false);
                }}
                className="block w-full text-left px-3 py-2 hover:bg-gray-100 text-sm"
              >
                Editar
              </button>
              <button
                onClick={() => onDeleteCard(card.id)}
                className="block w-full text-left px-3 py-2 hover:bg-red-100 text-sm text-red-600"
              >
                Eliminar
              </button>
            </div>
          )}
        </div>
      )}

      <div className="flex justify-between items-center mb-4">
        {editingTitle ? (
          <div className="flex items-center gap-2 w-full">
            <input
              className="border px-2 py-1 rounded flex-1"
              value={tempTitle}
              onChange={e => setTempTitle(e.target.value)}
            />
            <button
              onClick={() => {
                onUpdateCardTitle(card.id, tempTitle.trim() || card.title);
                setEditingTitle(false);
              }}
              className="text-sm text-blue-600 hover:underline"
            >
              Guardar
            </button>
          </div>
        ) : (
          <h3
            className="text-lg font-semibold cursor-text"
            onClick={() => setEditingTitle(true)}
          >
            {card.title}
          </h3>
        )}
      </div>

      <div className="space-y-2 mb-4">
        {card.tasks
        .filter(Boolean) 
        .map((task, idx) => (
          
          <div
            key={task.id}
            draggable
            onDragStart={e => {
              e.stopPropagation();
              onTaskDragStart(task.id, card.id);
              setDraggedTaskIndex(idx);
            }}
            onDragOver={e => {
              e.preventDefault();
              e.stopPropagation();
            }}
            onDrop={e => {
              e.stopPropagation();
              handleInternalDrop(idx);
            }}
            onDragEnd={e => {
              e.stopPropagation();
              setDraggedTaskIndex(null);
              onTaskDragEnd();
            }}
            className="cursor-grab"
            onClick={() => onTaskClick(task)}
          >
            <TaskItem task={task} />
          </div>
        ))}

        <div
          onDragOver={e => e.preventDefault()}
          onDrop={() => handleInternalDrop(null)}
          className="h-10"
        />
      </div>

      {showTaskInput ? (
        <div className="flex flex-col space-y-2">
          <input
            className="border px-2 py-1 rounded"
            value={newTaskTitle}
            onChange={e => setNewTaskTitle(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && handleAdd()}
          />
          <button onClick={handleAdd} className="py-1 bg-blue-600 text-white rounded">
            Añadir
          </button>
        </div>
      ) : (
        <button
          onClick={() => setShowTaskInput(true)}
          className="w-full py-2 bg-gray-200 rounded-md hover:bg-gray-300"
        >
          + Añadir tarea
        </button>
      )}
    </div>
  );
};

export default CardColumn;
