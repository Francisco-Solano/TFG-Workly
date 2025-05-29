import { BrowserRouter, Routes, Route, Navigate, useParams, useNavigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Projects from './pages/Projects';

const ProjectWrapper = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const handleBack = () => {
    navigate("/home");
  };

  return <Projects projectId={Number(id)} onBack={handleBack} />;
};

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Redirige "/" a "/login" */}
        <Route path="/" element={<Navigate to="/login" replace />} />

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />

        {/* Ruta para lista general de proyectos (opcional) */}
        <Route path="/projects" element={<Home />} />

        {/* Ruta para detalle de proyecto con parámetro */}
        <Route path="/proyecto/:id" element={<ProjectWrapper />} />

        {/* Ruta catch-all para cualquier otra no definida */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
