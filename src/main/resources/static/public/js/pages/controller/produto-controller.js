app.controller('produtoController', function($scope,produtoService, $routeParams){
	
	var self = this;
		
	
	 self.cadastrarProduto = function(produto){
			 produtoService.produtoCreate(self.produto);
			 
			 self.produto = produto=[];
	}
	 
	 self.buscarProdutos = function(){
		 produtoService.produtoFindAll().
			then(function(t){
				self.listaProduto = t;
				}, function(errResponse){
				toastr.error('Erro ao tentar buscar produto');
			});
		};
	
	  
	
});