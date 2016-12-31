-- Grupo 35

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Morador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Morador` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Morador` (
  `Username` VARCHAR(64) NOT NULL,
  `Password` VARCHAR(64) NOT NULL,
  `Nome` VARCHAR(64) NOT NULL,
  `Contacto` INT NOT NULL,
  `Avatar` VARCHAR(512) NOT NULL,
  `Saldo` FLOAT NOT NULL,
  `Estado` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Solicitacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Solicitacao` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Solicitacao` (
  `Morador_Responsavel` VARCHAR(64) NOT NULL,
  `Morador` VARCHAR(64) NOT NULL,
  `Despesa` INT NOT NULL,
  `Prestacao` INT NOT NULL,
  `Valor` FLOAT NOT NULL,
  INDEX `fk_Solicitacao_ Morador1_idx` (`Morador_Responsavel` ASC),
  CONSTRAINT `fk_Solicitacao_ Morador1`
    FOREIGN KEY (`Morador_Responsavel`)
    REFERENCES `mydb`.`Morador` (`Username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Despesa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Despesa` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Despesa` (
  `ID` INT NOT NULL,
  `Morador_Responsavel` VARCHAR(45) NOT NULL,
  `Valor_pago_solicitado` FLOAT NOT NULL,
  `Valor_pago_confirmado` FLOAT NOT NULL,
  `Valor_total` FLOAT NOT NULL,
  `Descricao` VARCHAR(256) NOT NULL,
  `Tipo` VARCHAR(64) NOT NULL,
  `Data_limite` DATETIME NULL,
  `Estado` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Fracao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Fracao` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Fracao` (
  `Morador` VARCHAR(64) NOT NULL,
  `Valor_pago` FLOAT NOT NULL,
  `Valor_total` FLOAT NOT NULL,
  `prestacoes_pagas` INT NOT NULL,
  `temPrestacoes` INT NOT NULL,
  `Despesa` INT NOT NULL,
  INDEX `fk_Fracao_Despesa1_idx` (`Despesa` ASC),
  PRIMARY KEY (`Morador`, `Despesa`),
  CONSTRAINT `fk_Fracao_Despesa1`
    FOREIGN KEY (`Despesa`)
    REFERENCES `mydb`.`Despesa` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Prestacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Prestacao` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Prestacao` (
  `ID` INT NOT NULL,
  `Valor` FLOAT NOT NULL,
  `Data_limite` DATETIME NULL,
  `Despesa` INT NOT NULL,
  `Concluida` INT NOT NULL,
  `Morador` VARCHAR(64) NOT NULL,
  INDEX `fk_Prestacao_Fracao1_idx` (`Morador` ASC),
  PRIMARY KEY (`ID`, `Morador`, `Despesa`),
  CONSTRAINT `fk_Prestacao_Fracao1`
    FOREIGN KEY (`Morador`)
    REFERENCES `mydb`.`Fracao` (`Morador`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Notificacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Notificacao` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Notificacao` (
  `Info` VARCHAR(512) NOT NULL,
  `Morador` VARCHAR(64) NOT NULL,
  INDEX `fk_Notificacao_ Morador1_idx` (`Morador` ASC),
  CONSTRAINT `fk_Notificacao_ Morador1`
    FOREIGN KEY (`Morador`)
    REFERENCES `mydb`.`Morador` (`Username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
