var arrow = "<td class=\"text-end pe-0\">";
arrow += "<a href=\"#\" class=\"btn btn-icon btn-bg-light btn-active-primary btn-sm\">";
arrow += "<span class=\"svg-icon svg-icon-4\">";
arrow += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\">";
arrow += "<rect opacity=\"0.5\" x=\"18\" y=\"13\" width=\"13\" height=\"2\" rx=\"1\" transform=\"rotate(-180 18 13)\" fill=\"black\" />";
arrow += "<path d=\"M15.4343 12.5657L11.25 16.75C10.8358 17.1642 10.8358 17.8358 11.25 18.25C11.6642 18.6642 12.3358 18.6642 12.75 18.25L18.2929 12.7071C18.6834 12.3166 18.6834 11.6834 18.2929 11.2929L12.75 5.75C12.3358 5.33579 11.6642 5.33579 11.25 5.75C10.8358 6.16421 10.8358 6.83579 11.25 7.25L15.4343 11.4343C15.7467 11.7467 15.7467 12.2533 15.4343 12.5657Z\" fill=\"black\" />";
arrow += "</svg></span></a></td>";

let chat = "<td class=\"ps-0 py-3\">";
chat += "<div class=\"symbol symbol-65px bg-light-warning me-3\">";
chat += "<span class=\"symbol-label\"><span class=\"svg-icon svg-icon-1 svg-icon-warning\">";
chat += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\"> fill=\"black\" />";
chat += "<rect opacity=\"0.3\" x=\"13\" y=\"2\" width=\"9\" height=\"9\" rx=\"2\" fill=\"black\" />";
chat += "<rect opacity=\"0.3\" x=\"13\" y=\"13\" width=\"9\" height=\"9\" rx=\"2\" fill=\"black\" />";
chat += "<rect opacity=\"0.3\" x=\"2\" y=\"13\" width=\"9\" height=\"9\" rx=\"2\" fill=\"black\" />";
chat += "</svg></span></span></div></td>";

let board = "<th class=\"ps-0 py-3\">";
board += "<div class=\"symbol symbol-65px bg-light-success me-3\">";
board += "<span class=\"symbol-label\"><span class=\"svg-icon svg-icon-1 svg-icon-success\">";
board += "<svg xmlns =\"http://www.w3.org/2000/svg\" width=\"24\" height =\"24\" viewBox=\"0 0 24 24\" fill=\"none\">";
board += "<rect opacity =\"0.5\" x=\"11.364\" y=\"20.364\" width=\"16\" height =\"2\" rx=\"1\" transform =\"rotate(-90 11.364 20.364)\" fill =\"black\" />";
board += "<rect x =\"4.36396\" y=\"11.364\" width=\"16\" height=\"2\" rx =\"1\" fill=\"black\" />";
board += "</svg></span></span></div></th>";

let hexagon = "<th class=\"ps-0 py-3\">"
hexagon += "<div class=\"symbol symbol-65px bg-light-primary me-3\">"
hexagon += "<span class=\"symbol-label\">";
hexagon += "<span class=\"svg-icon svg-icon-1 svg-icon-primary\">";
hexagon += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\">";
hexagon += "<path opacity=\"0.3\" d=\"M20 3H4C2.89543 3 2 3.89543 2 5V16C2 17.1046 2.89543 18 4 18H4.5C5.05228 18 5.5 18.4477 5.5 19V21.5052C5.5 22.1441 6.21212 22.5253 6.74376 22.1708L11.4885 19.0077C12.4741 18.3506 13.6321 18 14.8167 18H20C21.1046 18 22 17.1046 22 16V5C22 3.89543 21.1046 3 20 3Z\" fill=\"black\" />";
hexagon += "<rect x=\"6\" y=\"12\" width=\"7\" height=\"2\" rx=\"1\" fill=\"black\" />";
hexagon += "<rect x=\"6\" y=\"7\" width=\"12\" height=\"2\" rx=\"1\" fill=\"black\" />";
hexagon += "</svg></span></span></div></th>";

let base = "<th class=\"ps-0 py-3\">";
base += "<div class=\"symbol symbol-65px bg-light-danger me-3\">";
base += "<span class=\"symbol-label\"><span class=\"svg-icon svg-icon-1 svg-icon-danger\">";
base += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\">";
base += "<path opacity=\"0.3\" d=\"M21.25 18.525L13.05 21.825C12.35 22.125 11.65 22.125 10.95 21.825L2.75 18.525C1.75 18.125 1.75 16.725 2.75 16.325L4.04999 15.825L10.25 18.325C10.85 18.525 11.45 18.625 12.05 18.625C12.65 18.625 13.25 18.525 13.85 18.325L20.05 15.825L21.35 16.325C22.35 16.725 22.35 18.125 21.25 18.525ZM13.05 16.425L21.25 13.125C22.25 12.725 22.25 11.325 21.25 10.925L13.05 7.62502C12.35 7.32502 11.65 7.32502 10.95 7.62502L2.75 10.925C1.75 11.325 1.75 12.725 2.75 13.125L10.95 16.425C11.65 16.725 12.45 16.725 13.05 16.425Z\" fill=\"black\" />";
base += "<path d=\"M11.05 11.025L2.84998 7.725C1.84998 7.325 1.84998 5.925 2.84998 5.525L11.05 2.225C11.75 1.925 12.45 1.925 13.15 2.225L21.35 5.525C22.35 5.925 22.35 7.325 21.35 7.725L13.05 11.025C12.45 11.325 11.65 11.325 11.05 11.025Z\" fill=\"black\" />";
base += "</svg></span></span></div></th>";

const icons = [chat, board, hexagon, base];

function populaTabelaCursos(data) {
   var line = "<tr>";
   let j = 0;

   for (let i = 0; i < data.length; i++) {
      if (i == icons.length) {
         j = 0;
      }

      line += icons[j];
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

$(document).ready(function () {

   axios.get('cursos').then(function (response) {
      var table = populaTabelaCursos(response.data);
      $("#table_cursos").html(table);
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
});

