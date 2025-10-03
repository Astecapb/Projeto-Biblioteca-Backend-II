const API = "http://localhost:8080/api/exemplares";
const form = document.getElementById("exemplarForm");
const tableBody = document.querySelector("#exemplaresTable tbody");

async function loadExemplares() {
  const res = await fetch(API);
  const data = await res.json();
  tableBody.innerHTML = "";
  data.forEach(e => {
    tableBody.innerHTML += `
      <tr>
        <td>${e.id}</td>
        <td>${e.codigo}</td>
        <td>${e.status}</td>
        <td>${e.livroId}</td>
        <td><button class="btn btn-danger btn-sm" onclick="deleteExemplar(${e.id})">Excluir</button></td>
      </tr>`;
  });
}

form.addEventListener("submit", async e => {
  e.preventDefault();
  const dto = {
    codigo: document.getElementById("codigo").value,
    status: document.getElementById("status").value,
    livroId: parseInt(document.getElementById("livroId").value)
  };
  await fetch(API, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(dto)
  });
  form.reset();
  loadExemplares();
});

async function deleteExemplar(id) {
  await fetch(`${API}/${id}`, { method: "DELETE" });
  loadExemplares();
}

loadExemplares();
