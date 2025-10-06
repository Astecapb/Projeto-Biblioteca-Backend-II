const API = "http://localhost:8080/api/autores";
const modal = new bootstrap.Modal('#modalAutor');

/* ---------- 1. LISTAR TODOS ---------- */
function carregarAutores() {
  fetch(API)
    .then(r => r.json())
    .then(lista => {
      const tbody = document.querySelector('#autoresTable tbody');
      tbody.innerHTML = lista.map(a => `
        <tr>
          <td>${a.id}</td>
          <td>${a.nome}</td>
          <td>${a.cpf}</td>
          <td>${a.email}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${a.id}', '${a.nome}','${a.cpf}','${a.email}')">Editar</button>
            <button class="btn btn-sm btn-danger" onclick="excluirAutor('${a.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

/* ---------- 2. ABRIR MODAL (NOVO / EDITAR) ---------- */
function abrirNovo() {
  limparForm();
  modal.show();
}

function abrirEditar(id, nome) {
  document.getElementById('autorId').value = id;
  document.getElementById('nome').value = nome;
  document.getElementById('cpf').value = cpf;
  document.getElementById('email').value = email;
  modal.show();
}

function limparForm() {
  ['autorId', 'nome', 'cpf', 'email'].forEach(id => {
    document.getElementById(id).value = '';
  });
}

/* ---------- 3. SALVAR (CREATE / UPDATE) ---------- */
function salvarAutor() {
  const id = document.getElementById('autorId').value;
  const nome = document.getElementById('nome').value.trim();
  const cpf = document.getElementById('cpf').value.trim();
  const email = document.getElementById('email').value.trim();

 if (!nome || !cpf || !email) {
    alert('Preencha todos os campos');
    return;
  }

  const dto = { nome, cpf, email };

  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

 fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto)
  })
    .then(r => {
      if (!r.ok) throw new Error('Erro ao salvar');
      return r.json();
    })
    .then(() => {
      modal.hide();
      carregarAutores();
    })
    .catch(err => alert(err.message));
}

/* ---------- 4. EXCLUIR ---------- */
function excluirAutor(id) {
  if (!confirm('Excluir autor?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarAutores());
}

/* ---------- 5. INICIALIZAÇÃO ---------- */
document.addEventListener('DOMContentReady', () => {
  // Botão "Novo Autor" do HTML
  document.getElementById('btnNovo')?.addEventListener('click', abrirNovo);
  carregarAutores();
});