const API = "http://localhost:8080/api/emprestimos";
const modalEmprestimo = new bootstrap.Modal('#modalEmprestimo');

/* ---------- 1. LISTAR / BUSCAR POR INTERVALO ---------- */
function carregarEmprestimos(page = 0, size = 20) {
  const inicio = document.getElementById('buscaInicio').value;
  const fim = document.getElementById('buscaFim').value;
  const url = inicio && fim
        ? `${API}/intervalo?inicio=${inicio}T00:00:00&fim=${fim}T23:59:59&page=${page}&size=${size}`
        : `${API}?page=${page}&size=${size}`;

  fetch(url)
    .then(r => r.json())
    .then(data => {
      const tbody = document.querySelector('#emprestimosTable tbody');
      tbody.innerHTML = (data.content || []).map(e => `
        <tr>
          <td>${e.id}</td>
          <td>${e.usuarioId ?? ''}</td>
          <td>${e.exemplarId ?? ''}</td>
          <td>${new Date(e.dataHoraEmprestimo).toLocaleString('pt-BR')}</td>
          <td>${e.status}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${e.id}','${e.usuarioId}','${e.exemplarId}','${e.dataHoraEmprestimo}','${e.status}')">Editar</button>
            <button class="btn btn-sm btn-info"    onclick="renovar('${e.id}')">Renovar</button>
            <button class="btn btn-sm btn-success" onclick="finalizar('${e.id}')">Finalizar</button>
            <button class="btn btn-sm btn-danger"  onclick="excluir('${e.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

/* ---------- 2. CARREGAR LISTAS (usuários e exemplares disponíveis) ---------- */
async function carregarListas() {
  // usuários
  const usuarios = await fetch('http://localhost:8080/api/usuarios?page=0&size=100').then(r => r.json());
  const usuarioSelect = document.getElementById('usuarioId');
  usuarioSelect.innerHTML = usuarios.content.map(u => `<option value="${u.id}">${u.nome} (${u.email})</option>`).join('');

  // exemplares DISPONÍVEIS
  const exemplares = await fetch('http://localhost:8080/api/exemplares?status=DISPONIVEL&page=0&size=100').then(r => r.json());
  const exemplarSelect = document.getElementById('exemplarId');
  exemplarSelect.innerHTML = exemplares.content.map(e => `<option value="${e.id}">${e.codigo}</option>`).join('');
}

/* ---------- 3. ABRIR MODAL (EDITAR / NOVO) ---------- */
function abrirEditar(id, usuarioId, exemplarId, dataHora, status) {
  document.getElementById('emprestimoId').value = id;
  document.getElementById('usuarioId').value = usuarioId;
  document.getElementById('exemplarId').value = exemplarId;
  document.getElementById('dataHoraEmprestimo').value = dataHora;
  document.getElementById('status').value = status;
  modalEmprestimo.show();
}

/* ---------- 4. SALVAR (CREATE / UPDATE) ---------- */
function salvarEmprestimo() {
  const id = document.getElementById('emprestimoId').value;
  const dto = {
    usuarioId: document.getElementById('usuarioId').value,
    exemplarId: document.getElementById('exemplarId').value,
    dataHoraEmprestimo: document.getElementById('dataHoraEmprestimo').value,
    status: document.getElementById('status').value
  };

  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

  fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto)
  }).then(() => {
    modalEmprestimo.hide();
    carregarEmprestimos();
  });
}

/* ---------- 5. RENOVAR / FINALIZAR / EXCLUIR ---------- */
function renovar(id) {
  fetch(`${API}/${id}/renovar`, { method: 'PUT' }).then(() => carregarEmprestimos());
}

function finalizar(id) {
  fetch(`${API}/${id}/finalizar`, { method: 'PUT' }).then(() => carregarEmprestimos());
}

function excluir(id) {
  if (!confirm('Excluir empréstimo?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarEmprestimos());
}

/* ---------- 6. INICIALIZAÇÃO ---------- */
document.addEventListener('DOMContentLoaded', () => {
  carregarListas();        // preenche selects
  carregarEmprestimos(0, 20); // lista empréstimos
});