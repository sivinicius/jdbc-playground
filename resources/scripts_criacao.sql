-- -----------------------------------------------------
-- Schema jdbc_playground
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `jdbc_playground` ;

-- -----------------------------------------------------
-- Schema jdbc_playground
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jdbc_playground` DEFAULT CHARACTER SET utf8 ;
USE `jdbc_playground` ;

-- -----------------------------------------------------
-- Table `jdbc_playground`.`tb_cursos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbc_playground`.`tb_cursos` ;

CREATE TABLE IF NOT EXISTS `jdbc_playground`.`tb_cursos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `ativo` VARCHAR(1) NOT NULL DEFAULT 'S',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jdbc_playground`.`tb_professores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbc_playground`.`tb_professores` ;

CREATE TABLE IF NOT EXISTS `jdbc_playground`.`tb_professores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jdbc_playground`.`tb_turmas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbc_playground`.`tb_turmas` ;

CREATE TABLE IF NOT EXISTS `jdbc_playground`.`tb_turmas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_curso` INT NOT NULL,
  `id_professor` INT NOT NULL,
  `data_inicio` DATETIME NOT NULL,
  `data_fim` DATETIME NULL,
  PRIMARY KEY (`id`, `id_curso`, `id_professor`),
  INDEX `fk_tb_cursos_has_tb_professores_tb_professores1_idx` (`id_professor` ASC),
  INDEX `fk_tb_cursos_has_tb_professores_tb_cursos_idx` (`id_curso` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_tb_cursos_has_tb_professores_tb_cursos`
    FOREIGN KEY (`id_curso`)
    REFERENCES `jdbc_playground`.`tb_cursos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_cursos_has_tb_professores_tb_professores1`
    FOREIGN KEY (`id_professor`)
    REFERENCES `jdbc_playground`.`tb_professores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
