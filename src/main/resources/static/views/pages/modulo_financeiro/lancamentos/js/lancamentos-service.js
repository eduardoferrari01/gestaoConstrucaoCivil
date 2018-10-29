app.factory('LancamentosService', function($rootScope, toastr, $http,$q){
	
	var url = "/rest/lancamentos";
	
	return{
		insert: function(lancamento){	
			return $http.post(url, lancamento)
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		
		update: function(lancamento, idLancamento){			
			return $http.put(url + "/" + idLancamento, lancamento)
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		pdf: function(filter){
			var config =  {filter : filter, responseType: 'arraybuffer'};
				return $http.post(url + '/imprimir',filter, { responseType: 'arraybuffer'} )
				.then(function(response){
					return response.data;
				},function(errResponse){
					return $q.reject(errResponse);
				});			
		},
		findById: function(param){
			return $http.get(url +'/'+ param)
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		
		filter: function(filter){
			var config = {params: {filter:filter}};
			return $http.post(url + '/filters', filter)
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		tipos: function(){
			return $http.get(url +'/tipos')
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		status: function(){
			return $http.get(url +'/status')
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		estatistica: function(){
			return $http.get(url +'/estatistica')
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		categorias: function(){
			return $http.get(url +'/categorias')
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
		deleteById: function(idLancamento){
			return $http.put(url + '/' + idLancamento)
			.then(function(response){
				return response.data;
			},function(errResponse){
				return $q.reject(errResponse);
			});
		},
	}
});