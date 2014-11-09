function Usuario(nombre, apellidos, profesion) {
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.profesion = profesion;
}

angular.module('controlUsuarios', [])
.controller('Controlador', [
'$scope',

function($scope){
  $scope.titulo = 'Bienvenido al control de usuarios';
  $scope.usuarios = [new Usuario('Daniel', 'Aguilar Guzman', 'Desarrollador'), 
  new Usuario('Angel', 'Ramos', 'Desarrollador')];
  
  $scope.agregarUsuario = function(nombre, apellidos, profesion) {
	if(!$scope.title || $scope.title === '') { return; }
  
	$scope.usuarios.push(new Usuario($scope.nombre, $scope.apellidos, $scope.profesion));
	$scope.nombre = '';
	$scope.apellidos = '';
	$scope.profesion = '';
  }
  
}]);