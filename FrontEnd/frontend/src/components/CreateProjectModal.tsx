import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

interface Props {
  onClose: () => void;
  onCreate: (
    title: string,
    id?: number,
    visibility?: string,
    collaborators?: Collaborator[]
  ) => void;
  editingProject?: { id: number; title: string };
}

interface Collaborator {
  email: string;
  id: number;
}

const CreateProjectModal: React.FC<Props> = ({
  onClose,
  onCreate,
  editingProject,
}) => {
  const { user } = useAuth();
  const token = user?.token;

  const [title, setTitle] = useState(editingProject?.title || "");
  const [visibility, setVisibility] = useState("private");
  const [collaboratorEmail, setCollaboratorEmail] = useState("");
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [error, setError] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const handleAddCollaborator = async () => {
    const email = collaboratorEmail.trim();
    setError("");
    setSuccessMsg("");

    if (!email) {
      setError("Introduce un correo válido.");
      return;
    }

    if (collaborators.find((c) => c.email === email)) {
      setError("Este colaborador ya ha sido añadido.");
      return;
    }

    try {
      if (!token) {
        setError("No estás autenticado.");
        return;
      }

      const response = await axios.get(
        "http://localhost:8080/api/v1/usuarios/buscar",
        {
          params: { email },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const usuarios = response.data;

      if (Array.isArray(usuarios) && usuarios.length > 0) {
        const usuario = usuarios[0];
        setCollaborators([
          ...collaborators,
          { email: usuario.email, id: usuario.id },
        ]);
        setCollaboratorEmail("");
        setSuccessMsg("Colaborador añadido localmente.");
      } else {
        setError("El usuario no fue encontrado.");
      }
    } catch (err: any) {
      if (err.response?.status === 404) {
        setError("El usuario no fue encontrado.");
      } else if (err.response?.status === 403) {
        setError("No tienes permisos para realizar esta acción.");
      } else {
        setError("Ocurrió un error al buscar el usuario.");
      }
    }
  };

const handleRemoveCollaborator = async (collaborator: Collaborator) => {
  setError("");

  // Eliminar localmente siempre
  setCollaborators(collaborators.filter((c) => c.id !== collaborator.id));

  // Si es un proyecto ya guardado, intentar también eliminar del backend
  if (editingProject?.id && token) {
    try {
      await axios.delete(
        `http://localhost:8080/api/v1/proyectos/${editingProject.id}/colaboradores/${collaborator.id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
    } catch (err) {
      console.error("Error eliminando colaborador del backend:", err);
      // Mostrar error pero no revertir el cambio local
      setError("No se pudo eliminar el colaborador del proyecto guardado.");
    }
  }
};


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!token) return;

    try {
      if (editingProject?.id) {
        for (const collaborator of collaborators) {
          await axios.post(
            `http://localhost:8080/api/v1/proyectos/${editingProject.id}/colaboradores`,
            null,
            {
              params: {
                usuarioId: collaborator.id,
                rol: "ROLE_USER",
              },
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
        }
        setSuccessMsg("Colaboradores añadidos correctamente.");
      }

      onCreate(title, editingProject?.id, visibility, collaborators);
    } catch (err) {
      console.error("Error añadiendo colaboradores:", err);
      setError("Hubo un problema al añadir colaboradores.");
    }
  };

  useEffect(() => {
    const fetchCollaborators = async () => {
      if (editingProject?.id && token) {
        try {
          const res = await axios.get(
            `http://localhost:8080/api/v1/proyectos/${editingProject.id}/colaboradores`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );

          const fetched = res.data as { email: string; id: number }[];
          const mapped = fetched.map((u) => ({
            email: u.email,
            id: u.id,
          }));

          setCollaborators(mapped);
        } catch (err) {
          console.error("Error cargando colaboradores:", err);
          setError("No se pudieron cargar los colaboradores.");
        }
      }
    };

    fetchCollaborators();
  }, [editingProject?.id, token]);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md relative animate-fadeIn">
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-gray-500 hover:text-gray-700 text-2xl font-bold"
        >
          &times;
        </button>

        <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">
          {editingProject ? "Editar Proyecto" : "Crear Proyecto"}
        </h2>

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">
              Título del proyecto
            </label>
            <input
              type="text"
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              className="w-full border border-gray-300 rounded-md py-2 px-3"
            />
          </div>

          <div className="mb-4">
            <label htmlFor="visibility" className="block text-sm font-medium text-gray-700 mb-1">
              Visibilidad
            </label>
            <select
              id="visibility"
              value={visibility}
              onChange={(e) => setVisibility(e.target.value)}
              className="w-full border border-gray-300 rounded-md py-2 px-3"
            >
              <option value="public">Público</option>
              <option value="private">Privado</option>
            </select>
          </div>

          <div className="mb-6">
            <label htmlFor="collaboratorEmail" className="block text-sm font-medium text-gray-700 mb-1">
              Participantes (opcional)
            </label>
            <div className="flex gap-2">
              <input
                type="email"
                id="collaboratorEmail"
                value={collaboratorEmail}
                onChange={(e) => setCollaboratorEmail(e.target.value)}
                placeholder="Correo electrónico del colaborador"
                className="flex-grow border border-gray-300 rounded-md py-2 px-3"
              />
              <button
                type="button"
                onClick={handleAddCollaborator}
                className="bg-blue-600 text-white font-bold px-4 rounded-md hover:bg-blue-700"
              >
                Añadir
              </button>
            </div>

            {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
            {successMsg && <p className="text-green-600 text-sm mt-2">{successMsg}</p>}

            {collaborators.length > 0 && (
              <ul className="mt-3 space-y-1">
                {collaborators.map((collab) => (
                  <li
                    key={collab.id}
                    className="flex items-center justify-between bg-gray-100 px-3 py-1 rounded-md text-sm"
                  >
                    {collab.email}
                    <button
                      type="button"
                      onClick={() => handleRemoveCollaborator(collab)}
                      className="text-red-500 hover:text-red-700 text-xs ml-2"
                    >
                      Eliminar
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>

          <button
            type="submit"
            className="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded-md hover:bg-blue-700"
          >
            {editingProject ? "Guardar Cambios y Colaboradores" : "Crear"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateProjectModal;
