import './App.css'

function App() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-6">
      <h1 className="text-4xl font-extrabold text-blue-600 underline mb-4">
        Hello Tailwind CSS! 🚀
      </h1>

      <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg shadow-lg transition-all">
        Click Me!
      </button>

      <div className="mt-6 w-64 p-4 bg-white shadow-md rounded-lg border border-gray-300">
        <p className="text-gray-700">
          Este es un cuadro con borde, sombra y padding.
        </p>
      </div>

      <div className="mt-6 grid grid-cols-2 gap-4">
        <div className="p-4 bg-green-400 text-white font-semibold rounded-lg">
          Caja 1
        </div>
        <div className="p-4 bg-red-400 text-white font-semibold rounded-lg">
          Caja 2
        </div>
      </div>
    </div>
  )
}

export default App
