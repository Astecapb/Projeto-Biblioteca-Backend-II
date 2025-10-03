const API = "http://localhost:8080/api/livros";
const modalLivro   = new bootstrap.Modal('#modalLivro');
const modalExemplares = new bootstrap.Modal('#modalExemplares');

/* ---------- 1. LISTAGEM / BUSCA POR TÍTULO (PAGINADA) ---------- */
function carregarLivros(page = 0, size = 20) {
  const titulo = document.getElementById('buscaTitulo').value.trim();
  const url = titulo
        ? `${API}?titulo=${encodeURIComponent(titulo)}&page=${page}&size=${size}`
        : `${API}?page=${page}&size=${size}`;

  fetch(url)
    .then(r => r.json())
    .then(data => {
      const tbody = document.querySelector('#livrosTable tbody');
      tbody.innerHTML = (data.content || []).map(l => `
        <tr>
          <td>${l.id}</td>
          <td>${l.titulo}</td>
          <td>${l.ano ?? ''}</td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="abrirEditar('${l.id}','${l.titulo}',${l.ano},'${l.isbn}','${l.editora}')">Editar</button>
            <button class="btn btn-sm btn-info"    onclick="verExemplares('${l.id}')">Exemplares</button>
            <button class="btn btn-sm btn-danger" onclick="excluirLivro('${l.id}')">Excluir</button>
          </td>
        </tr>`).join('');
    });
}

/* ---------- 2. MODAL DE EDIÇÃO ---------- */
function abrirEditar(id, titulo, ano, isbn, editora) {
  document.getElementById('livroId').value   = id;
  document.getElementById('titulo').value    = titulo;
  document.getElementById('ano').value       = ano ?? '';
  document.getElementById('isbn').value      = isbn ?? '';
  document.getElementById('editora').value   = editora ?? '';
  modalLivro.show();
}

const conservacao = document.getElementById('statusConservacao').value;
const dto = {
  codigo: codigo,
  status: status,
  livroId: livroId,
  statusConservacao: conservacao   // novo campo
};

/* ---------- 3. SALVAR (CREATE / UPDATE) ---------- */
function salvarLivro() {
  const id      = document.getElementById('livroId').value;
  const titulo  = document.getElementById('titulo').value.trim();
  const ano     = parseInt(document.getElementById('ano').value) || null;
  const isbn    = document.getElementById('isbn').value.trim();
  const editora = document.getElementById('editora').value.trim();
  
  /* ✅ NOVO CAMPO – conservação */
  const dto = {
    titulo,
    isbn,
    editora,
    ano,
    autoresIds: []   // preencha quando tiver select de autores
  };

  console.log('JSON enviado:', dto);

  const metodo = id ? 'PUT' : 'POST';
  const url    = id ? `${API}/${id}` : API;

  fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto)
  }).then(() => {
    modalLivro.hide();
    carregarLivros();
  });
}

/* ---------- 4. EXCLUIR ---------- */
function excluirLivro(id) {
  if (!confirm('Excluir livro?')) return;
  fetch(`${API}/${id}`, { method: 'DELETE' }).then(() => carregarLivros());
}

/* ---------- 5. MODAL DE CONTAGEM DE EXEMPLARES ---------- */
async function verExemplares(livroId) {
  const modal  = new bootstrap.Modal('#modalExemplares');
  const body   = document.getElementById('exemplaresBody');

  // todos os status
  const todos = await fetch(`${API}/${livroId}/exemplares/count`).then(r => r.json());
  // filtro único (exemplo: disponíveis)
  const disp  = await fetch(`${API}/${livroId}/exemplares/count?status=DISPONIVEL`).then(r => r.json());

  body.innerHTML = `
  <p><strong>Total de exemplares:</strong> ${todos.DISPONIVEL + todos.EMPRESTADO + todos.RESERVADO}</p>
  <ul class="list-unstyled">
    <li>Disponíveis: <span class="badge bg-success">${todos.DISPONIVEL}</span></li>
    <li>Emprestados: <span class="badge bg-warning">${todos.EMPRESTADO}</span></li>
    <li>Reservados: <span class="badge bg-secondary">${todos.RESERVADO}</span></li>
  </ul>
  <hr>
  <p class="mb-0"><em>Filtro único (Disponíveis):</em> 
     <span class="badge bg-success">${disp["DISPONÍVEL"] ?? disp["DISPONIVEL"] ?? 0}</span>
  </p>
`;

  modal.show();
}

/* ---------- 6. INICIALIZAÇÃO ---------- */

document.addEventListener('DOMContentLoaded', () => carregarLivros(0, 20));