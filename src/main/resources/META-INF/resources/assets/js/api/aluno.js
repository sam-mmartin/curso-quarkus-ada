
import listItems from './pagination.js';
import { arrow, chat, board, hexagon, base } from './elements.js';

const icons = [chat, board, hexagon, base];


function retornaQuantidadePaginas(quantidadeItems, itemsPorPagina) {
   return quantidadeItems / itemsPorPagina;
}

function criarTabela(data, pageActual, showActive) {
   var line = "";

   if (showActive) {
      line = "<div class=\"tab-pane fade show active\" id=\"kt_tab_pane_" + pageActual + "\" role=\"tabpanel\" aria-labelledby=\"kt_tab_pane_" + pageActual + "\">";
   } else {
      line = "<div class=\"tab-pane fade\" id=\"kt_tab_pane_" + pageActual + "\" role=\"tabpanel\" aria-labelledby=\"kt_tab_pane_" + pageActual + "\">";
   }

   line += "<div class=\"table-responsive\">";
   line += "<table class=\"table table-borderless align-middle\">";
   line += "<thead><tr><th class=\"p-0 w-50px\"></th>";
   line += "<th class=\"p-0 min-w-200px\"></th><th class=\"p-0 min-w-100px\"></th>";
   line += "<th class=\"p-0 min-w-40px\"></th></tr></thead><tbody>";

   let trs = populaTabelaCursos(data);
   line += trs + "</tbody></table></div></div>";

   return line;
}

function populaTabelaCursos(data) {
   var line = "";
   let j = 0;

   for (let i = 0; i < data.length; i++) {
      if (i == icons.length) {
         j = 0;
      }

      line += "<tr>" + icons[j];
      line += "<td class=\"ps-0\">";
      line += "<a href=\"#\" class=\"text-gray-800 fw-bolder text-hover-primary fs-6\">";
      line += data[i].nomeDoCurso + "</a></td>";

      line += "<td><div class=\"d-flex flex-column w-100 me-3\">";
      line += "<div class=\"d-flex flex-stack mb-2\">";
      line += "<span class=\"text-dark me-2 fs-6 fw-bolder\">Progresso</span></div>";

      line += "<div class=\"d-flex align-items-center\">";
      line += "<div class=\"progress h-6px w-100 bg-light-primary\">";

      line += "<div class=\"progress-bar bg-primary\" role=\"progressbar\" style=\"width: 46%;\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div>";

      line += "</div><span class=\"text-muted fs-7 fw-bold ps-3\">46%</span>";
      line += "</div></div></td>" + arrow + "</tr>";

      j++;
   }

   return line;
}

function populaTabelaAlunos(data) {
   var tr_td = "<tr><td class=\"p - 0 py - 3\">";
   var div = "<div class=\"symbol symbol-55px mt - 1 me - 5\">";
   var span = "<span class=\"symbol-label bg-light-warning align-items-end\">";
   var img = "<img alt=\"Logo\" src=\"/assets/media/svg/avatars/047-girl-25.svg\" class=\"mh-40px\" />";
   var closeTag = "</span></div></td>";
   var line;

   for (let index = 0; index < data.length; index++) {
      line += tr_td + div + span + img + closeTag;
      line += "<td class=\"px-0\">";
      line += "<a href=\"#\" class=\"text-gray-800 fw-bolder text-hover-primary fs-6\">"
      line += data[index].nome + "</a>";
      line += "<span class=\"text-muted fw-bold d-block mt-1\">";
      line += data[index].curso + "</span></td>";

      line += "<td></td>";

      line += "<td class=\"text-end\">";
      line += "<span class=\"text-gray-800 fw-bolder d-block fs-6\">";
      line += data[index].matricula + "</span>";
      line += "<span class=\"text-muted fw-bold d-block mt-1 fs-7\">";
      line += data[index].cpf + "</span></td>";

      line += "<td class=\"text-end\">";
      line += "<span class=\"fw-bolder text-primary\"></span>";
      line += data[index].estado + "</span></td>";

      line += arrow + "</tr>";
   }

   return line;
}

function populaTabelaProfessor(data) {
   var tr_td = "<tr><td class=\"p - 0 py - 3\">";
   var div = "<div class=\"symbol symbol-55px mt - 1 me - 5\">";
   var span = "<span class=\"symbol-label bg-light-warning align-items-end\">";
   var img = "<img alt=\"Logo\" src=\"/assets/media/svg/avatars/047-girl-25.svg\" class=\"mh-40px\" />";
   var closeTag = "</span></div></td>";
   var line;

   for (let index = 0; index < data.length; index++) {
      line += tr_td + div + span + img + closeTag;
      line += "<td class=\"px-0\">";
      line += "<a href=\"#\" class=\"text-gray-800 fw-bolder text-hover-primary fs-6\">"
      line += data[index].nome + "</a></td>";

      line += "<td></td>";

      line += "<td class=\"text-end\">";
      line += "<span class=\"text-gray-800 fw-bolder d-block fs-6\">";
      line += data[index].matricula + "</span>";
      line += "<span class=\"text-muted fw-bold d-block mt-1 fs-7\">";
      line += data[index].cpf + "</span></td>";

      line += "<td class=\"text-end\">";
      line += "<span class=\"fw-bolder text-primary\"></span>";
      line += data[index].estado + "</span></td>";

      line += arrow + "</tr>";
   }

   return line;
}

function inserePaginacao(totalPage) {
   let line = "<li class=\"page-item previous disabled\">";
   line += "<a href=\"#\" class=\"page-link\"><i class=\"previous\"></i></a>";
   line += "</li>";

   for (let i = 0; i < totalPage; i++) {

      if (i == 0) {
         line += "<li class=\"page-item active\">";
      } else {
         line += "<li class=\"page-item \">";
      }

      line += "<a href=\"#kt_tab_pane_" + (i + 1) + "\" class=\"page-link\">" + (i + 1) + "</a>";
      line += "</li>";

   }

   line += "<li class=\"page-item next\">";
   line += "<a href=\"#\" class=\"page-link\"><i class=\"next\"></i></a>";
   line += " </li >";

   return line;
}

window.onload = function () {

   axios.get('cursos').then(function (response) {
      let quantidade = Object.keys(response.data).length;
      let paginas = retornaQuantidadePaginas(quantidade, 5);

      for (let i = 0; i < paginas; i++) {
         let items = listItems(response.data, (i + 1), 5);
         let showActive = false;

         if (i == 0) {
            showActive = true;
         }

         var table = criarTabela(items, (i + 1), showActive);
         $("#myTabCursos").append(table);
      }

      $("#cursos_pagination").html(inserePaginacao(paginas));
   })

   axios.get('/alunos').then(function (response) {
      let quantidade = Object.keys(response.data).length;
      var table = populaTabelaAlunos(response.data);

      $("#alunos_matriculados").html(quantidade);
      $("#quant_alunos").html(quantidade);
      $("#table_alunos").html(table);
   });

   axios.get('/professores').then(function (response) {
      var table = populaTabelaProfessor(response.data);
      $("#quant_professor").html(Object.keys(response.data).length);
      $("#table_professor").html(table);
   });
}

