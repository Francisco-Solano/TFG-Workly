// src/services/authService.ts
const API_URL = "http://localhost:8080/auth";

export const loginUser = async (email: string, password: string) => {
  console.log("→ Enviando:", { email, password });

  const res = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  });

  const body = await res.text();
  console.log("← Respuesta raw:", body);

  if (!res.ok) {
    throw new Error("Login fallido");
  }

  return JSON.parse(body);
};


export const registerUser = async (
  username: string,
  email: string,
  password: string,
  password2: string
) => {
  const res = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ username, email, password, password2 })
  });

  if (!res.ok) {
    throw new Error("Registro fallido");
  }

  return res.json();
};
