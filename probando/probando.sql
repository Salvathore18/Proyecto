-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-11-2014 a las 06:56:16
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `probando`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `descuento`
--

CREATE TABLE IF NOT EXISTS `descuento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carnet` varchar(45) DEFAULT NULL,
  `sueldo` varchar(45) DEFAULT NULL,
  `descuento` varchar(45) DEFAULT NULL,
  `marcar_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_descuento_marcar1_idx` (`marcar_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `descuento`
--

INSERT INTO `descuento` (`id`, `carnet`, `sueldo`, `descuento`, `marcar_id`) VALUES
(1, '013', '100', '10', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE IF NOT EXISTS `empleado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carnet` varchar(10) DEFAULT NULL,
  `nombres` varchar(45) DEFAULT NULL,
  `apellidos` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `genero` varchar(45) DEFAULT NULL,
  `especialidad` varchar(45) DEFAULT NULL,
  `dui` varchar(10) DEFAULT NULL,
  `nit` varchar(17) DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `sueldo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id`, `carnet`, `nombres`, `apellidos`, `direccion`, `genero`, `especialidad`, `dui`, `nit`, `fecha_nac`, `sueldo`) VALUES
(1, '013', 'Alejandra', 'Garcia', '12 Calle Poniente Analco', 'Femenino', 'Sub Gerente', '12345678-9', '123-325432-123', '1994-07-06', 100);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE IF NOT EXISTS `horarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carnet` varchar(10) DEFAULT NULL,
  `horario_entrada` time DEFAULT '08:00:00',
  `horario_salida` time DEFAULT '16:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Volcado de datos para la tabla `horarios`
--

INSERT INTO `horarios` (`id`, `carnet`, `horario_entrada`, `horario_salida`) VALUES
(1, '013', '08:30:00', '16:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcar`
--

CREATE TABLE IF NOT EXISTS `marcar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carnet` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `horario` time NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_marcar_horarios1_idx` (`tipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `marcar`
--

INSERT INTO `marcar` (`id`, `carnet`, `fecha`, `horario`, `tipo`) VALUES
(1, '013', '2014-11-02', '03:00:00', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `descuento`
--
ALTER TABLE `descuento`
  ADD CONSTRAINT `fk_descuento_marcar1` FOREIGN KEY (`marcar_id`) REFERENCES `marcar` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `marcar`
--
ALTER TABLE `marcar`
  ADD CONSTRAINT `fk_marcar_horarios1` FOREIGN KEY (`tipo`) REFERENCES `horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
