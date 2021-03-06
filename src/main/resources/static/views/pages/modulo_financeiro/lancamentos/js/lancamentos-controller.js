app.controller("LancamentosCadastarController", LancamentosCadastarController);
app.controller("LancamentosEditarController", LancamentosEditarController);
app.controller("LancamentosListarController", LancamentosListarController);
app.controller("LancamentosShowController", LancamentosShowController);
app.controller("LancamentosEstatisticaController", LancamentosEstatisticaController);

function LancamentosCadastarController($localStorage, $state, $stateParams, LancamentosService, CategoriaFinanceiroService, toastr, $scope, $rootScope){
	
	var self = this;	
	self.findAllIdTipo = findAllIdTipo;
	self.submit = submit;
	
		
	tipos();
	operacoes();
	
	$scope.obj = {};
	
	$rootScope.visualizar = visualizar;
	function visualizar(param){
		var Tipo = param;
 		$state.go('lancamentos.list', {Tipo});
 	};		
	  function submit(form){
		  if(form.$invalid){
				sweetAlert({title: "Por favor preencha os campos obrigatorios", 	type : "error", timer : 100000,   width: 500,  padding: 20});	
				return;
			}
		 	var file = $scope.obj.flow.files[0]
	    	var form = new FormData();
		 	if(file){
		 		form.append('file', file.file);	
		 	}		    	    	
	    	form.append('lancamento',new Blob([JSON.stringify(self.lancamento)], {
	            type: "application/json"
	        }) )		        
			LancamentosService.insert(form)
		   	 .then(function(response){
		   		estatistica();
				toastr.success("Lancamento, cadastrado");					
				clear(form);		
		   	 	},
			function(errResponse){		
				 swal({ timer : 30000, text: errResponse.data.message ,  type : "error", width: 500, higth: 100, padding: 20}).catch(swal.noop);
				 });
		  };
			  
	  function operacoes(){
			LancamentosService.tipos().
			then(function(p){
				self.operacoes = p;
				}, function(errResponse){
			});
		};
				
	function tipos(){
		CategoriaFinanceiroService.tipos().
		then(function(p){
			self.tipos = p;
			}, function(errResponse){
		});
	};
	
	function findAllIdTipo(idTipo){
		CategoriaFinanceiroService.findAllIdTipo(idTipo).
		then(function(p){
			self.categorias = p;
			}, function(errResponse){
		});
	};
	
	function clear(form){
		 $scope.obj.flow.cancel();
		 self.lancamento = {};
		 $scope.form.$setPristine();
		 $scope.form.$setUntouched();   	
     }
	
	function estatistica(){
 		LancamentosService.estatistica().
 		then(function(p){
 			$scope.$evalAsync(function () {
   	    	 $rootScope.estatistica = p;  
   		  });
 			}, function(errResponse){
 		});
 	};
	 	 
			
}		

function LancamentosEditarController($localStorage, $state, $stateParams, CategoriaFinanceiroService, LancamentosService, toastr, $scope, $rootScope){
	
	var self = this;
	
	var idLancamentos = $stateParams.idLancamentos;
	
	self.submit = submit;
	self.findAllIdTipo = findAllIdTipo;
	$scope.obj = {};
	tipos(); 
	operacoes();
	findById(idLancamentos);
	$rootScope.visualizar = visualizar;
	
	
	function visualizar(param){
		var Tipo = param;
 		$state.go('lancamentos.list', {Tipo});
 	};
	
  function submit(form){
	  if(form.$invalid){
			sweetAlert({title: "Por favor preencha os campos obrigatorios", 	type : "error", timer : 100000,   width: 500,  padding: 20});	
			return;
		}
	 	var file = $scope.obj.flow.files[0];
    	var form = new FormData();
	 	if(file){
	 		form.append('file', file.file);	
	 	}
		form.append('id',new Blob([JSON.stringify(self.lancamento.id)], {
            type: "application/json"
        }) )
    	form.append('lancamento',new Blob([JSON.stringify(self.lancamento)], {
            type: "application/json"
        }) )		        
		LancamentosService.update(form)
	   	 .then(function(response){
	   		estatistica();
			toastr.success("Lancamento, alterado");
			$scope.obj.flow.cancel();
			clear(form);		
	   	 	},
		function(errResponse){		
			 swal({ timer : 30000, text: errResponse.data.message ,  type : "error", width: 500, higth: 100, padding: 20}).catch(swal.noop);
			 });
	  };
	  
	  function operacoes(){
			LancamentosService.tipos().
			then(function(p){
				self.operacoes = p;
				}, function(errResponse){
			});
		};
					
	function tipos(){
		CategoriaFinanceiroService.tipos().
		then(function(p){
			self.tipos = p;
			}, function(errResponse){
		});
	};
	
	function findAllIdTipo(idTipo){
		CategoriaFinanceiroService.findAllIdTipo(idTipo).
		then(function(p){
			self.categorias = p;
			}, function(errResponse){
		});
	};
	
	
	function clear(form){	    	
    	 self.lancamento = null;  
    	 $state.go("lancamentos.consultar");
    	
     }
		
	 	 
	function findById(id){
		if(!id)return;
		LancamentosService.findById(id).
		then(function(p){
			self.lancamento = p;
			findAllIdTipo(p.categoria.tipo);
			}, function(errResponse){
		});
	};
	
	function estatistica(){
 		LancamentosService.estatistica().
 		then(function(p){
 			$scope.$evalAsync(function () {
   	    	 $rootScope.estatistica = p;  
   		  });
 			}, function(errResponse){
 		});
 	};
	
}

function LancamentosListarController(blockUI, LancamentosService, toastr, $scope, $rootScope, $stateParams, $state){
	
	var self = this;
	
	var tipo = $stateParams.Tipo;
	self.page = {page : 0 ,linesPerPage : 24 , orderBy : 'id' , direction : 'ASC'};
	$scope.lancamentoFilter = {page : self.page};
	$rootScope.visualizar = visualizar;
	$scope.titulo = 'TODOS';
	if(tipo){
		$scope.titulo = tipo;
	   var adicional = tipo;
	   $scope.lancamentoFilter = {page : self.page, adicional : adicional};
	}
	self.deleteById = deleteById;
	self.sort = sort;	
	$scope.filter = filter;
	$scope.pesquisar = pesquisar;
	self.show = show;
	self.modalComprovante = modalComprovante;
	$scope.imprimir = 'PAGINA';
	
	filter($scope.lancamentoFilter);
	tipos();
	categorias();
	status();
	estatistica();
	
	
		
	function sort(orderBy){		
		$scope.lancamentoFilter.page.orderBy = orderBy;
		$scope.lancamentoFilter.page.direction == 'ASC' ? $scope.lancamentoFilter.page.direction = 'DESC' : $scope.lancamentoFilter.page.direction = 'ASC';
		filter($scope.lancamentoFilter);
	}
	
	function pesquisar(lancamentoFilter, imprimir){
		if(imprimir == 'PAGINA'){
			filter(lancamentoFilter);
		}else{
			pdf(lancamentoFilter);
		}
	}
	
	    	    
	    function filter(lancamentoFilter){
	    	$scope.mensagemErro = null;	    	 
	    	 blockUI.start();
	    	 lancamentoFilter.page.page == '0'? lancamentoFilter.page.page = 0 : lancamentoFilter.page.page = lancamentoFilter.page.page - 1;   
	    	 LancamentosService.filter(lancamentoFilter).
	    	 then(function(e){
	    		 $scope.mensagemErro = null;
	    		 self.lancamentos = e.content;	
	    		 $scope.lancamentoFilter.page.totalElementos = e.totalElements;
	    		 $scope.lancamentoFilter.page.totalPaginas = e.totalPages;
	    		 $scope.lancamentoFilter.page.page = e.number + 1;
	    		 blockUI.stop();
	    	 }, function(errResponse){
	    		 self.lancamentos = [];
	    		 blockUI.stop();
	    		 if(errResponse.status == 404){
	    			 $scope.mensagemErro = errResponse.data.mensagem;
	    		 }else{
	    			 $scope.mensagemErro = errResponse.data.mensagem;
	    		 }
			 });
	    }
	    
	    function pdf(lancamentoFilter){	
	    	$scope.mensagemErro = null;	    	 
	    	 blockUI.start();
	    	 lancamentoFilter.page.page == '0'? lancamentoFilter.page.page = 0 : lancamentoFilter.page.page = lancamentoFilter.page.page - 1;  
	    	 LancamentosService.pdf(lancamentoFilter)
	   	 .then(function(d){
	   		var file = new Blob([d],{type: 'application/pdf'});
	   		var fileURL = URL.createObjectURL(file);
	   		blockUI.stop();
	   	    window.open(fileURL);
	   	 	 }).catch(function error(msg) {			 	   	 		 
	    	});
     };	     
     
     function tipos(){
 		LancamentosService.tipos().
 		then(function(p){
 			self.tipos = p;
 			}, function(errResponse){
 		});
 	};
 	
 	function status(){
 		LancamentosService.status().
 		then(function(p){
 			self.status = p;
 			}, function(errResponse){
 		});
 	};
 	
 	function categorias(){
 		LancamentosService.categorias().
 		then(function(p){
 			self.categorias = p;
 			}, function(errResponse){
 		});
 	};
 	
 	function estatistica(){
 		LancamentosService.estatistica().
 		then(function(p){
 			 $scope.$evalAsync(function () {
    	    	 $rootScope.estatistica = p;  
    		  });
 			}, function(errResponse){
 		});
 	};
	  
	   function deleteById(id) {
			swal({ 
				  title: 'Deletar!!!',
				  text: "Tem certeza que deseja deletar este lançamento ?",
				  type: 'info',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'Excluir!'
				}).then(function () {
					LancamentosService.deleteById(id).
			then(function(response){				
				filter($scope.lancamentoFilter);				
				toastr.success("Lancamentos, Excluido");
				estatistica()
			});
		})
	}
	   
	   function visualizar(param){
		   $scope.titulo = param;
		   $scope.lancamentoFilter = {page : self.page, adicional : param};
		   filter($scope.lancamentoFilter);
	 	};
	 	
 	 function show(lancamento) {
			$scope.lancamento = lancamento;
		}
 	 
 	 function modalComprovante(lancamento){
 		$scope.lancamento = lancamento;
 	 	}
}


function LancamentosShowController( $state, $stateParams, LancamentosService, $scope, blockUI){
	
	var self = this;
	
	var idLancamentos = $stateParams.idLancamentos;
	self.visualizarItem = visualizarItem;
	self.imprimirLancamentos = imprimirLancamentos;
	
	$scope.visualizarItem = false;
	findById(idLancamentos);
	
	function findById(id){
		if(!id)return;
		LancamentosService.findById(id).
		then(function(p){
			self.lancamentos = p;
			if(p.statusLancamentos == 'FECHADO'){
					findByVencedores(idLancamentos);
					self.titulo = 'Vencedores';
				}else{
					findByConcorrentes(idLancamentos);
					self.titulo = 'Concorrentes';
				}
			}, function(errResponse){
		});
	};
	
	function findByConcorrentes(idLancamentos){
		if(!idLancamentos)return;
		LancamentosService.findByConcorrentes(idLancamentos).
		then(function(p){
			self.empresas = p;
			}, function(errResponse){
		});
	};
	
	function findByVencedores(idLancamentos){
		if(!idLancamentos)return;
		LancamentosService.findByVencedores(idLancamentos).
		then(function(p){
			self.empresas = p;
			}, function(errResponse){
		});
	};
	
	function visualizarItem(item){
		$scope.item = item;
		$scope.visualizarItem == true ? $scope.visualizarItem = false : $scope.visualizarItem = true;
	}
	
	 function imprimirLancamentos(idLancamentos){	
    	 blockUI.start();
    	 LancamentosService.imprimirLancamentos(idLancamentos)
   	 .then(function(d){
   		var file = new Blob([d],{type: 'application/pdf'});
   		var fileURL = URL.createObjectURL(file);
   		blockUI.stop();
   	    window.open(fileURL);
   	 	 }).catch(function error(msg) {			 	   	 		 
    	});
};	     
}

function LancamentosEstatisticaController(blockUI, LancamentosService, toastr, $scope, $rootScope, $state){
	
	var self = this;
	
	estatistica();
	
	$rootScope.visualizar = visualizar;
	function visualizar(param){
		console.log(param);
 		var Tipo = param;
 		$state.go('lancamentos.list', {Tipo});
 	};
 	
 	function estatistica(){
 		LancamentosService.estatistica().
 		then(function(p){
 			$rootScope.estatistica = p;
 			}, function(errResponse){
 		});
 	};
 	
 	
 	 	
}


