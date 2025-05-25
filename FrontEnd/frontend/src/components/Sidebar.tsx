import React, { useState, useContext } from "react";
import LogoutModal from "./LogoutModal";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Sidebar: React.FC = () => {
  const [showLogoutModal, setShowLogoutModal] = useState(false);
const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout(); // Limpiar el token
    navigate("/login");
  };

  return (
    <>
      <aside className="w-64 bg-white shadow-md flex flex-col rounded-r-lg">
        <div className="p-6 border-b border-gray-200">
          <h1 className="text-3xl font-bold text-gray-700">Workly</h1>
        </div>
        <nav className="flex-1 py-6">
          <ul>
            <li className="mb-2">
              <a href="#" className="block py-2 px-6 bg-gray-200 text-gray-900 font-semibold border-l-4 border-blue-600 rounded-r-md">
                Proyectos
              </a>
            </li>
            <li className="mb-2">
              <a href="#" className="block py-2 px-6 text-gray-700 hover:bg-gray-100 rounded-r-md transition-colors duration-200">
                Calendario
              </a>
            </li>
            <li className="mb-2">
              <a href="#" className="block py-2 px-6 text-gray-700 hover:bg-gray-100 rounded-r-md transition-colors duration-200">
                Destacados
              </a>
            </li>
          </ul>
        </nav>
        <div className="p-6 border-t border-gray-200">
          <h2 className="text-lg font-semibold text-gray-700 mb-4">Ajustes</h2>
          <ul>
            <li>
              <button
                onClick={() => setShowLogoutModal(true)}
                className="flex items-center text-gray-700 hover:bg-gray-100 py-2 px-6 rounded-md transition-colors duration-200 w-full text-left"
              >
                <svg className="w-5 h-5 mr-2 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H5a3 3 0 01-3-3V7a3 3 0 013-3h5a3 3 0 013 3v1" />
                </svg>
                Cerrar sesión
              </button>
            </li>
          </ul>
        </div>
      </aside>

      {showLogoutModal && (
        <LogoutModal
          onConfirm={handleLogout}
          onCancel={() => setShowLogoutModal(false)}
        />
      )}
    </>
  );
};

export default Sidebar;
