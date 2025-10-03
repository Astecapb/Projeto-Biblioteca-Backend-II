const API = "http://localhost:8080/api/exemplares";
const STATUS = ['DISPONIVEL', 'EMPRESTADO', 'RESERVADO'];
const modal = new bootstrap.Modal('#modalExemplar');

/* ---------- 1. LISTAR TODOS ---------- */
function carregarExemplares() {
  fetch(API)
    .then(r => r.json())
    .then(lista => {
      const tbody = document.querySelector('#exemplaresTable tbody');
      tbody.innerHTML = lista.map(e => `
        <tr>
          <td>${e.id}</td>
          <td>${e.codigo}</td>
          <td>${e.status}</td>
          <td>${e.livroId}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${e.id}','${e.codigo}','${e.status}',${e.livroId})">Editar</button>
            <button class="btn btn-sm btn-danger"  onclick="excluirExemplar('${e.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

/* ---------- 2. ABRIR MODAL (EDITAR / NOVO) ---------- */
function abrirEditar(id, codigo, status, livroId) {
  document.getElementById('exemplarId').value = id;
  document.getElementById('codigo').value = codigo;
  document.getElementById('status').value = status;
  document.getElementById('livroId').value = livroId;
  modal.show();
}

/* ---------- 3. SALVAR (CREATE / UPDATE) ---------- */
function salvarExemplar() {
  const id = document.getElementById('exemplarId').value;
  const codigo = document.getElementById('codigo').value.trim();
  const status = document.getElementById('status').value;
  const livroId = parseInt(document.getElementById('livroId').value);
  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

  fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ codigo, status, livroId })
  }).then(() => {
    modal.hide();
    carregarExemplares();
  });
}

function excluirExemplar(id) {
  if (!confirm('Excluir exemplar?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarExemplares());
}

document.addEventListener('DOMContentLoaded', carregarExemplares);