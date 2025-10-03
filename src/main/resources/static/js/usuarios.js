const API = "http://localhost:8080/api/usuarios";
const modal = new bootstrap.Modal('#modalUsuario');

/* ---------- 1. LISTAR TODOS (PAGINADO) ---------- */
function carregarUsuarios(page = 0, size = 20) {
  fetch(`${API}?page=${page}&size=${size}`)
    .then(r => r.json())
    .then(data => {
      const tbody = document.querySelector('#usuariosTable tbody');
      tbody.innerHTML = (data.content || []).map(u => `
        <tr>
          <td>${u.id}</td>
          <td>${u.nome}</td>
          <td>${u.email}</td>
          <td>${u.cpf}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${u.id}','${u.nome}','${u.email}','${u.cpf}')">Editar</button>
            <button class="btn btn-sm btn-danger"  onclick="excluir('${u.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

/* ---------- 2. ABRIR MODAL (EDITAR / NOVO) ---------- */
function abrirEditar(id, nome, email, cpf) {
  document.getElementById('usuarioId').value   = id;
  document.getElementById('nome').value        = nome;
  document.getElementById('email').value       = email;
  document.getElementById('cpf').value         = cpf;
  modal.show();
}

/* ---------- 3. SALVAR (CREATE / UPDATE) ---------- */
function salvarUsuario() {
  const id      = document.getElementById('usuarioId').value;
  const nome    = document.getElementById('nome').value.trim();
  const email   = document.getElementById('email').value.trim();
  const cpf     = document.getElementById('cpf').value.trim();

  const dto = { nome, email, cpf };

  const metodo = id ? 'PUT' : 'POST';
  const url    = id ? `${API}/${id}` : API;

  fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto)
  }).then(() => {
    modal.hide();
    carregarUsuarios();
  });
}

/* ---------- 4. EXCLUIR ---------- */
function excluir(id) {
  if (!confirm('Excluir usuário?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' })
    .then(() => carregarUsuarios());
}

/* ---------- 5. INICIALIZAÇÃO ---------- */
document.addEventListener('DOMContentLoaded', () => carregarUsuarios(0, 20));