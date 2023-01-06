INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (1, 'MATEMÁTICA', '2022-12-27 18:02:51.049854000', '2022-12-27 18:02:51.049854000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (2, 'PORTUGUÊS', '2022-12-27 18:14:09.756751000', '2022-12-27 18:14:09.756751000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (3, 'BIOLOGIA', '2022-12-27 18:16:09.993404000', '2022-12-27 18:16:09.993404000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (4, 'HISTÓRIA GERAL', '2022-12-27 18:16:18.950119000', '2022-12-27 18:16:18.950119000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (5, 'HISTÓRIA DO BRASIL', '2022-12-27 18:17:38.376906000', '2022-12-27 18:17:38.376906000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (6, 'GEOGRAFIA', '2022-12-27 18:17:38.376906000', '2022-12-27 18:17:38.376906000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (7, 'QUÍMICA GERAL', '2022-12-27 18:17:59.696200000', '2022-12-27 18:17:59.696200000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (8, 'QUÍMICA ANALÍTICA', '2022-12-27 18:18:19.765532000', '2022-12-27 18:18:19.765532000');
INSERT INTO DISCIPLINA (id, nomeDaDisciplina, data_criacao, data_atualizacao) VALUES (9, 'FÍSICA', '2022-12-27 18:18:27.178578000', '2022-12-27 18:18:27.178578000');

INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000', 'ANÁLISES QUÍMICA','Curso implantado');
INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (2, '2022-12-27 18:19:21.716580000', '2022-12-27 18:19:21.716580000', 'Técnico em Design de Interiores', 'Curso implantado');
INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (3, '2022-12-27 18:19:32.746305000', '2022-12-27 18:19:32.746305000', 'Técnico em Administração', 'Curso implantado');
INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (4, '2022-12-27 18:19:44.382664000', '2022-12-27 18:19:44.382664000', 'Técnico em Óptica', 'Curso implantado');
INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (5, '2022-12-27 18:19:56.686055000', '2022-12-27 18:19:56.686055000', 'Técnico em Desenvolvimento de Sistemas', 'Curso implantado');
INSERT INTO CURSO (id, data_criacao, data_atualizacao, nomeDoCurso,  observacao) VALUES (6, '2022-12-27 18:20:11.895232000', '2022-12-27 18:20:11.895232000', 'Técnico em Segurança do Trabalho', 'Curso implantado');

INSERT INTO ALUNO (id, nome, cpf, matricula, estado, curso_id, data_criacao, data_atualizacao) VALUES (1, 'Unknow 1', '546.212.310-81', '1010-100', 1, 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000');
INSERT INTO ALUNO (id, nome, cpf, matricula, estado, curso_id, data_criacao, data_atualizacao) VALUES (2, 'Unknow 2', '546.212.310-81', '1010-200', 1, 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000');
INSERT INTO ALUNO (id, nome, cpf, matricula, estado, curso_id, data_criacao, data_atualizacao) VALUES (3, 'Unknow 3', '546.212.310-81', '1010-300', 0, 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000');

INSERT INTO PROFESSOR (id, nome, cpf, matricula, estado, data_criacao, data_atualizacao, observacao) VALUES (1, 'Samuel', '546.212.310-81', '1000-100', 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000', 'Contrato iniciado');

INSERT INTO CURSO_PROFESSOR (id, curso_id, professor_id, data_criacao, data_atualizacao) VALUES (1, 1, 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000');
INSERT INTO PROFESSOR_DISCIPLINA (id, disciplina_id, professor_id, data_criacao, data_atualizacao) VALUES (1, 1, 1, '2022-12-27 18:00:58.892711000','2022-12-27 18:00:58.892711000');