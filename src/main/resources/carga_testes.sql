INSERT INTO ALUNO (id, nome, cpf, matricula, estado) VALUES (1, 'Juliana Cintra', '001.002.003-04', '1010-100', 1);
INSERT INTO ALUNO (id, nome, cpf, matricula, estado) VALUES (2, 'Mônica Priscylla', '111.222.333-44', '2020-200', 1);
INSERT INTO ALUNO (id, nome, cpf, matricula, estado) VALUES (3, 'Mallu Estácio', '101.202.303-40', '3030-300', 0);

INSERT INTO PROFESSOR (id, nome, cpf, matricula, estado) VALUES (1, 'Samuel', '004.005.003-38', '1000-000', 1);

INSERT INTO DISCIPLINA (id, nomeDaDisciplina) VALUES (1, 'MATEMÁTICA');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina) VALUES (2, 'PORTUGUÊS');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina) VALUES (3, 'QUÍMICA');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina) VALUES (4, 'FÍSICA');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina) VALUES (5, 'Fisolofia');

INSERT INTO CURSO (id, nomeDoCurso) VALUES (1, 'ANÁLISES QUÍMICA');
INSERT INTO CURSO (id, nomeDoCurso) VALUES (2, 'TÉCNICO EM INFORMÁTICA');
INSERT INTO CURSO (id, nomeDoCurso) VALUES (3, 'REDES DE COMPUTADORES');
INSERT INTO CURSO (id, nomeDoCurso) VALUES (4, 'TÉCNICO EM ALIMENTOS');
INSERT INTO CURSO (id, nomeDoCurso) VALUES (5, 'TEC EM BIOTECNLOGIA');

INSERT INTO CURSO_DISCIPLINA (curso_id, disciplina_id) VALUES (1, 1);
INSERT INTO CURSO_DISCIPLINA (curso_id, disciplina_id) VALUES (2, 1);
INSERT INTO CURSO_DISCIPLINA (curso_id, disciplina_id) VALUES (3, 1);
INSERT INTO CURSO_DISCIPLINA (curso_id, disciplina_id) VALUES (4, 1);
INSERT INTO CURSO_DISCIPLINA (curso_id, disciplina_id) VALUES (5, 3);

INSERT INTO DISCIPLINA_PROFESSOR (disciplina_id, professor_id) VALUES (1, 1);
INSERT INTO DISCIPLINA_PROFESSOR (disciplina_id, professor_id) VALUES (3, 1);
INSERT INTO DISCIPLINA_PROFESSOR (disciplina_id, professor_id) VALUES (4, 1);

INSERT INTO CURSO_PROFESSOR (curso_id, professor_id) VALUES (2, 1);