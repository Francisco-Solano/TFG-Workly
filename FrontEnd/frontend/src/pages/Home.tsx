import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Header from '../components/Header';
import CreateProjectModal from '../components/CreateProjectModal';
import { useAuth } from "../context/AuthContext";

interface Project {
  id: number;
  title: string;
  favorite: boolean;
}

const Home = () => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [showModal, setShowModal] = useState(false);

  const { user } = useAuth();
  const token = user?.token;

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        console.log("Token usado para obtener proyectos:", token);

        const res = await fetch("http://localhost:8080/api/v1/proyectos/mios", { 
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (!res.ok) throw new Error("Error al cargar proyectos");

        const data = await res.json();
        console.log("Proyectos recibidos:", data);

        // Adaptar la estructura de datos recibidos a la interfaz Project
        const adapted = data.map((p: any) => ({
          id: p.proyectoId,
          title: p.nombre,
          favorite: false, // o como lo manejes (por defecto false)
        }));

        setProjects(adapted);

      } catch (err) {
        console.error(err);
      }
    };

    if (token) fetchProjects();
  }, [token]);

  const handleCreateProject = async (title: string) => {
  try {
    console.log("Token usado para crear proyecto:", token);

    const res = await fetch("http://localhost:8080/api/v1/proyectos/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ nombre: title, visibilidad: false }),
    });

    if (!res.ok) throw new Error("Error al crear proyecto");

    const newProject = await res.json();
    const adaptedProject = {
      id: newProject.proyectoId,
      title: newProject.nombre,
      favorite: false,
    };

    setProjects((prev) => [...prev, adaptedProject]);
    setShowModal(false);
  } catch (err) {
    console.error(err);
  }
};


  const toggleFavorite = (id: number) => {
    setProjects(projects.map(project =>
      project.id === id ? { ...project, favorite: !project.favorite } : project
    ));
  };

  return (
    <div className="flex h-screen bg-gray-100 font-sans">
      <Sidebar />
      <div className="flex flex-col flex-1">
        <Header onNavigateToCreateProject={() => setShowModal(true)} />
        <main className="flex-1 p-6">
          <h1 className="text-3xl font-bold text-gray-800 mb-6">Proyectos</h1>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
            {projects.map(project => (
              <div
                key={project.id}  // Ahora sí hay un id único para key
                className="bg-blue-800 text-white p-6 rounded-lg shadow-md flex justify-between items-center"
              >
                <span>{project.title}</span>
                <button onClick={() => toggleFavorite(project.id)}>
                  {project.favorite ? (
                    <svg className="w-5 h-5 fill-current text-white" viewBox="0 0 20 20">
                      <path d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 18.343 3.172 11.515a4 4 0 010-5.656z" />
                    </svg>
                  ) : (
                    <svg className="w-5 h-5 text-white" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M20.84 4.61a5.5 5.5 0 00-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 00-7.78 7.78l1.06 1.06L12 21.23l7.72-7.72 1.06-1.06a5.5 5.5 0 000-7.82z" />
                    </svg>
                  )}
                </button>
              </div>
            ))}
          </div>
        </main>
      </div>

      {showModal && (
        <CreateProjectModal
          onClose={() => setShowModal(false)}
          onCreate={(title) => handleCreateProject(title)}
        />
      )}
    </div>
  );
};

export default Home;
