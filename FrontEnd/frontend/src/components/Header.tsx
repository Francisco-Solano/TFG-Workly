import React from 'react';

// 1. Define una interfaz para las props que este componente va a recibir.
//    En este caso, una función llamada 'onNavigateToCreateProject'
//    que no toma argumentos y no devuelve nada (void).
interface HeaderProps {
  onNavigateToCreateProject: () => void;
}

// 2. Desestructura la prop 'onNavigateToCreateProject' del objeto de props.
const Header: React.FC<HeaderProps> = ({ onNavigateToCreateProject }) => {
  return (
    <header className="bg-white shadow-sm p-4 flex justify-between items-center rounded-bl-lg">
      {/* 3. Añade el evento onClick al botón "Crear" */}
      <button
        className="bg-blue-600 text-white px-4 py-2 rounded-md font-semibold hover:bg-blue-700 transition-colors duration-200 shadow-md"
        // Al hacer clic, se ejecutará la función 'onNavigateToCreateProject'
        // que se pasa desde el componente padre (App.tsx).
        onClick={onNavigateToCreateProject}
      >
        Crear
      </button>
      <div className="relative">
        <input
          type="text"
          placeholder="Busca tus proyectos"
          className="pl-10 pr-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent w-64 text-gray-700"
        />
        <svg className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
        </svg>
      </div>
    </header>
  );
};

export default Header;