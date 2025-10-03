const API = "http://localhost:8080/api/autores";
const form = document.getElementById("autorForm");
const tableBody = document.querySelector("#autoresTable tbody");

async function loadAutores() {
  const res = await fetch(API);
  const data = await res.json();
  tableBody.innerHTML = "";
  data.forEach(a => {
    tableBody.innerHTML += `
      <tr>
        <td>${a.id}</td>
        <td>${a.nome}</td>
        <td>
          <button class="btn btn-danger btn-sm" onclick="deleteAutor(${a.id})">Excluir</button>
        </td>
      </tr>`;
  });
}

form.addEventListener("submit", async e => {
  e.preventDefault();
  const nome = document.getElementById("nome").value;
  await fetch(API, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({nome})
  });
  form.reset();
  loadAutores();
});

async function deleteAutor(id) {
  await fetch(`${API}/${id}`, { method: "DELETE" });
  loadAutores();
}

loadAutores();
