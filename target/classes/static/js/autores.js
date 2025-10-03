const API = "http://localhost:8080/api/autores";
const modal = new bootstrap.Modal('#modalAutor');

function carregarAutores() {
  fetch(API)
    .then(r => r.json())
    .then(lista => {
      const tbody = document.querySelector('#autoresTable tbody');
      tbody.innerHTML = lista.map(a => `
        <tr>
          <td>${a.id}</td>
          <td>${a.nome}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${a.id}', '${a.nome}')">Editar</button>
            <button class="btn btn-sm btn-danger" onclick="excluirAutor('${a.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

function abrirEditar(id, nome) {
  document.getElementById('autorId').value = id;
  document.getElementById('nome').value = nome;
  modal.show();
}

function salvarAutor() {
  const id = document.getElementById('autorId').value;
  const nome = document.getElementById('nome').value.trim();
  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

  fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ nome })
  }).then(() => {
    modal.hide();
    carregarAutores();
  });
}

function excluirAutor(id) {
  if (!confirm('Excluir autor?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarAutores());
}

document.addEventListener('DOMContentLoaded', carregarAutores);