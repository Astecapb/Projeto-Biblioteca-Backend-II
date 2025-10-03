const API = "http://localhost:8080/api/livros";
const form = document.getElementById("livroForm");
const tableBody = document.querySelector("#livrosTable tbody");

async function loadLivros() {
  const res = await fetch(API);
  const data = await res.json();
  tableBody.innerHTML = "";
  (data.content || []).forEach(l => {
    tableBody.innerHTML += `
      <tr>
        <td>${l.id}</td>
        <td>${l.titulo}</td>
        <td>${l.ano || ""}</td>
        <td><button class="btn btn-danger btn-sm" onclick="deleteLivro(${l.id})">Excluir</button></td>
      </tr>`;
  });
}

form.addEventListener("submit", async e => {
  e.preventDefault();
  const dto = {
    titulo: document.getElementById("titulo").value,
    isbn: document.getElementById("isbn").value,
    editora: document.getElementById("editora").value,
    ano: parseInt(document.getElementById("ano").value) || null,
    autoresIds: document.getElementById("autoresIds").value
      .split(",").map(s => parseInt(s)).filter(n => !isNaN(n))
  };
  await fetch(API, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(dto)
  });
  form.reset();
  loadLivros();
});

async function deleteLivro(id) {
  await fetch(`${API}/${id}`, { method: "DELETE" });
  loadLivros();
}

loadLivros();
