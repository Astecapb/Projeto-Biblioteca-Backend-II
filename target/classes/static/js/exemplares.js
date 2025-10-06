const API = "http://localhost:8080/api/exemplares";
const STATUS = ['DISPONIVEL', 'EMPRESTADO', 'RESERVADO'];
const CONSERVACAO = ['NOVO', 'BOM', 'REGULAR'];
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
          <td>${e.statusConservacao}</td>
          <td>${e.livroId}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${e.id}','${e.codigo}','${e.status}','${e.statusConservacao}',${e.livroId})">Editar</button>
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
  document.getElementById('statusConservacao').value  = statusConservacao;
  document.getElementById('livroId').value = livroId;
  modal.show();
}

/* ---------- 3. SALVAR (CREATE / UPDATE) ---------- */
function salvarExemplar() {
  const id = document.getElementById('exemplarId').value;
  const codigo = document.getElementById('codigo').value.trim();
  const status = document.getElementById('status').value;
  const statusConservacao = document.getElementById('statusConservacao').value;
  const livroId = parseInt(document.getElementById('livroId').value.trim());


 if (!codigo || !livroId) { alert('Código e Livro-ID são obrigatórios'); return; }

  const dto = {
    codigo,
    status,
    statusConservacao,
    livroId
  };

  
  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

  /*fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ codigo, status, livroId })
  }).then(() => {
    modal.hide();
    carregarExemplares();
  });
}*/
fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto)
  })
  .then(r => { if (!r.ok) throw new Error('Erro ao salvar'); return r; })
  .then(() => {
    modal.hide();
    carregarExemplares();
  })
  .catch(err => alert(err.message));
}

/* ---------- 4. EXCLUIR ---------- */
function excluirExemplar(id) {
  if (!confirm('Excluir exemplar?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarExemplares());
}

/* ---------- 5. INIT ---------- */
document.addEventListener('DOMContentLoaded', carregarExemplares);