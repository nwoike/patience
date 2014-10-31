(function(document) {
    'use strict';

	$.ajaxSetup({
		async: false
	});
	
    var solitaire = {};
  
    solitaire.Repository = function(rootUrl) {
	  this.rootUrl = rootUrl;      
    };
  
    solitaire.Repository.prototype = {
      
      createGame: function() {
          return $.ajax({
            url: this.rootUrl,
            type: "POST"
          });
      },
      
      loadGame: function(gameId) {
         return $.ajax({
            url: this.rootUrl + gameId,
			dataType: "json",
            type: "GET"
          });
      },
        
     gameScore: function(gameId) {
         return $.ajax({
            url: this.rootUrl + gameId + "/score",
			dataType: "json",
            type: "GET"
          });
      },
      
      drawCard: function(gameId) {
         return $.ajax({
            url: this.rootUrl + gameId + "/draw",			 
            type: "POST"
         });
      },
      
      moveCards: function(gameId, cards, tableauPileId) {
         var cardsJson = [];
        
         for (var i=0, len=cards.length; i < len; i++) {
           var card = cards[i];

           cardsJson[i] = {
             "rank": card.rank,
             "suit": card.suit  
           };          
         }

         return $.ajax({
            url: this.rootUrl + gameId + "/move",
            dataType: "json",
			contentType: "application/json",
            type: "POST",
            data: JSON.stringify({
                "cards": cardsJson,
                "tableauPileId": tableauPileId
            })
          });
      },
      
      promoteCard: function(gameId, card, foundationId) {
         var that = this;
        
         return $.ajax({
            url: this.rootUrl + gameId + "/promote/",
            dataType: "json",
			contentType: "application/json",
            type: "POST",
            data: JSON.stringify({
			   "foundationId": foundationId,
               "rank": card.rank,
               "suit": card.suit
            })
          });
      },
      
      flipCard: function(gameId, tableauPileId) {
          var that = this;
         
          return $.ajax({
            url: this.rootUrl + gameId + "/flip",
            dataType: "json",
			contentType: "application/json",
            type: "POST",
            data: JSON.stringify({
               "tableauPileId": tableauPileId
            })
          });
      }
    };
  
    solitaire.Game = function() {
        this.repository = new solitaire.Repository('http://localhost:8081/klondike/');
        this.gameId = -1;
        this.mostRecentGameData = undefined;
        this.score = 0;
		this.spinner = document.querySelector('es-spinner');
		this.scorer = document.querySelector('es-score');
        this.stock = document.querySelector('es-stock');
        this.waste = document.querySelector('es-waste');
        this.foundations = {};
        this.tableau = {};
 
        var tableauPiles = document.querySelectorAll('es-tableau-pile');

		// TODO: Get ids from server instead?
		for (var i=0, len=tableauPiles.length; i < len; i++) {
          var tableauPile = tableauPiles[i]; 
          var tableauPileId = tableauPile.getAttribute("id");
          this.tableau[tableauPileId] = tableauPile;
        }     
		
		var foundationPiles = document.querySelectorAll('es-foundation');
		
		for (i=0, len=foundationPiles.length; i < len; i++) {
			var foundation = foundationPiles[i];
			var foundationId = foundation.getAttribute("id");
			this.foundations[foundationId] = foundation;
		}
    }

    solitaire.Game.prototype = {       
      
		toggleLoading: function() {
            this.spinner.toggle();	            
		},
		
        createGame: function() {
			this.toggleLoading();
            this.score = 0;
             
            this.repository.createGame().done(function(gameData) {			  
                this.mostRecentGameData = gameData;                
                this.gameId = gameData.gameId;
                this.render(gameData);   
                this.scorer.updateScore(0);
				
                window.location.hash = '/klondike/' + this.gameId;
            }.bind(this)).always(this.toggleLoading.bind(this));    
        },
      
        loadGame: function(gameId) {
            this.toggleLoading();
            
            this.repository.loadGame(gameId).done(function(gameData) {
                this.mostRecentGameData = gameData;             
                this.gameId = gameData.gameId;
                this.render(gameData);  
                this.updateScore();
                
            }.bind(this)).fail(function() {
                this.createGame();
            }.bind(this)).always(this.toggleLoading.bind(this));
        },
        
        updateScore: function() {
            this.repository.gameScore(this.gameId).done(function(scoreData) {
                 this.score = scoreData.score; 
                 this.scorer.updateScore(this.score);   
              }.bind(this));
        },
        
        drawCard: function() {
            this.toggleLoading();
            
            if (this.stock.isEmpty() && this.waste.isEmpty()) {
                return;
            }
            
            this.repository.drawCard(this.gameId).done($.proxy(function(gameData) {
                this.mostRecentGameData = gameData;        
                this.render(gameData);               
            }, this)).fail(function() {
                 this.render();
             }.bind(this)).always(this.toggleLoading.bind(this));
        },

        moveCards: function(cards, tableauPileId) {
            this.toggleLoading();
            
            this.repository.moveCards(this.gameId, cards, tableauPileId).done($.proxy(function(gameData) {
                this.mostRecentGameData = gameData;
                this.render(gameData);
                this.updateScore();
                
            }, this)).fail(function() {
                 this.render();
             }.bind(this)).always(this.toggleLoading.bind(this));
        },
      
        promoteCard: function(card, foundationId) {
             this.toggleLoading();
            
             this.repository.promoteCard(this.gameId, card, foundationId).done($.proxy(function(gameData) {
               this.mostRecentGameData = gameData;       
               this.render(gameData);
               this.updateScore();
                 
             }, this)).fail(function() {
                 this.render();
             }.bind(this)).always(this.toggleLoading.bind(this));
        },

        flipCard: function(tableauPileId) {
             this.toggleLoading();             
             
             this.repository.flipCard(this.gameId, tableauPileId).done($.proxy(function(gameData) {
                this.mostRecentGameData = gameData;          
                this.render(gameData);
                this.updateScore();
                 
             }, this)).fail(function() {
                 this.render();
             }.bind(this)).always(this.toggleLoading.bind(this));
        },        

        createCard: function(rank, suit) {
            var card = document.createElement("es-card");

            card.setAttribute("rank", rank);
            card.setAttribute("suit", suit); 

            return card;
        },
                
        render: function(gameData) {
            if (!gameData) {
                gameData = this.mostRecentGameData;   
            }
            
            // Cleanup
            var cards = document.querySelectorAll("es-card");
          
            for (var i=0, len=cards.length; i < len; i++) {
                var card = cards[i];
                card.parentNode.removeChild(card);
            }
            
            // Stock
            this.stock.setAttribute('size', gameData.stock.cardCount);

            // Waste
            var wasteData = gameData.waste;
            var wasteCards = [];
            
            for (i=0, len=gameData.waste.cards.length; i < len; i++) {
                var cardData = gameData.waste.cards[i];    
                var card = this.createCard(cardData.rank, cardData.suit);
                wasteCards.push(card);
            }
			
            this.waste.addCards(wasteCards);
            
            // Foundations
			for (var foundationId in this.foundations) {
				var foundation = this.foundations[foundationId];
				var foundationData = gameData.foundations[foundationId];
				
				var suit = foundationData.suit;
                var ranks = foundationData.ranks;

                var foundationCards = [];
                
                for (i=0, len=ranks.length; i < len; i++) {
                    var card = this.createCard(ranks[i], suit);
                    foundationCards.push(card);
                }
                
                foundation.loadCards(foundationCards);
			}

            // Tableau Piles
            for (var tableauPileId in this.tableau) {
                var tableauPile = this.tableau[tableauPileId];
                var tableauPileData = gameData.tableau[tableauPileId];

                tableauPile.setAttribute("unflippedCount", tableauPileData.cardCount - tableauPileData.flipped.length);

                var tableauCards = [];
                
                for (i=0, len=tableauPileData.flipped.length; i < len; i++) {
                    var cardData = tableauPileData.flipped[i];
                    var card = this.createCard(cardData.rank, cardData.suit);                   
                    tableauCards.push(card);
                }
                
                tableauPile.addCards(tableauCards, true);
            }
          
			if (gameData.won) {   
				this.animateWin();
                this.updateScore();
				return;
			} 
			
            this.initialize();
        }, 
      
		animateWin: function() {	
			var game = this;
			var foundations = this.foundations;
			                       
            // Locate next promotable tableau card
			function next() {
				var candidates = $('es-card:last-of-type:not(es-foundation es-card)');
				var found = false;
				
				candidates.each(function() {					
					for (var foundationId in foundations) {						
						var foundation = foundations[foundationId];		

						if (foundation.isAddable(this)) {
							found = {
								foundation: foundation,
								card: this
							};
							
							return false;
						}
					}
				});		
				
				return found;
			}
					
            // Animate card from its tableau pile to the correct foundation
			function animate(obj) {	
				var callback = $.proxy(function() {
					game.promoteCard(this.card, this.foundation.getAttribute("id"));
				}, obj);

				var card = $(obj.card);
				var dropOffset = $(obj.foundation).offset();
				var dragOffset = card.offset();
				var pile = card.closest('es-tableau-pile');
				var spacingOffset = pile.flippedCards && pile.flippedCards().length == 0 ? 0 : pile.flippedSpacing || 0;

				card.zIndex(9999);

				card.animate({
					top: "+=" + (dropOffset.top - dragOffset.top + spacingOffset),
					left: "+=" + (dropOffset.left - dragOffset.left)
				}, 300, 'swing', callback);
			}
			
			var obj = next();
			
			if (obj) {
				animate(obj);
				
			} else { 
				setTimeout(function() {
					game.showMenu();
				}, 1000);
			}
		},
		
		showMenu: function() {
            var prompt = document.querySelector('es-prompt');
            prompt.show();
		},
		
        initialize: function() {
          var game = this;

          function updateDraggables() {
              $(".ui-draggable").draggable("destroy");
              $(".ui-droppable").droppable("destroy");

              $("es-waste > es-card:last-child, es-foundation > es-card:last-child, es-tableau-pile > es-card").draggable({ 
                  appendTo: "body",
                  revert: function(isValid) {
                      if (!this.data("ui-draggable").options) return;

                      var revertDuration = this.data("ui-draggable").options.revertDuration;

                      if (!isValid) {
                          // Fixes original element flashing during revert animation due to custom helper usage 
                          $(this).nextAll('es-card').addBack().delay(revertDuration).show(0);
                          return "revert";
                      } 
                  },
                  containment: "window",
                  distance: 10,
                  scroll: false,
                  start: function() {      
                      $(this).nextAll('es-card').addBack().hide();
                  },
                  helper: function() {
                      var stack = $("<es-stack></es-stack>")[0];
                      var cards = $(this).nextAll('es-card').addBack().clone().css('top', 0).css('left', 0);

                      for (var i=0, len=cards.length; i<len; i++) {
                         stack.appendChild(cards[i]);     
                      }
                      
                      return $('<div></div>').zIndex(9999).append(stack);
                  }
              });

              $("es-waste > es-card:last-child, es-tableau-pile > es-card:last-child").unbind('dblclick').dblclick(function() {
                  var card = $(this).get(0);
				 
				  for (var i=1; i <= 4; i++) {
					 var foundation = game.foundations[i];

					 if (foundation.isAddable(card)) {
						var id = foundation.getAttribute("id");
						game.promoteCard(card, id);
						return;
					 }
				  }
              });
              
              updateDroppables();
          }

          function updateDroppables() {
              $("es-foundation").droppable({
                  hoverClass: "droppable",
                  tolerance: "pointer",
                  accept: function(card) {
                      if (!card) return false;					
                      return card.length > 0 && this.isAddable(card.get(0));
                  },			
                  drop: onDrop
              });

              // :empty didn't work
              $("es-tableau-pile").filter(function() { return $(this).children().length == 0; }).droppable({
                  hoverClass: "droppable",
                  tolerance: "pointer",
                  accept: function(card) {
                      if (!card) return false;
                      return card.length > 0 && this.isAddable(card.get(0));		
                  }, 
                  drop: onDrop
              });

              $("es-tableau-pile > es-card:last-child").droppable({
                  hoverClass: "droppable",
                  tolerance: "pointer",
                  greedy: true,
                  accept: function(card) {	
                      if (!card) return false;					
                      var tableauPile = $(this).closest("es-tableau-pile").get(0);					
                      return tableauPile && tableauPile.isAddable(card.get(0));
                  },
                  drop: onDrop
              });

              $('es-tableau-pile es-card-back:last-child').unbind('click').click(function(event) {
                  event.stopPropagation();
                 
                  var tableauPile = $(this).parents("es-tableau-pile").get(0);
                  var tableauPileId = tableauPile.getAttribute("id");
                
                  game.flipCard(tableauPileId);

                  return false;
              });
          }
            
          function onDrop(event, ui) {
              $(".ui-draggable").draggable({ disabled: true });
              $("es-waste > es-card:last-child, es-tableau-pile > es-card:last-child").unbind('dblclick');
              $('es-tableau-pile es-card-back:last-of-type').unbind('click');

              var helper = ui.helper.clone().appendTo("body");					
              var pile = $(this).is('es-card') ? $(this).parents("es-tableau-pile").get(0) : this;

              var dropOffset = $(this).offset();
              var dragOffset = helper.offset();

              var callback = $.proxy(function(cards, helper) {	
                  this.addCard ? this.addCard(cards[0]) : this.addCards(cards, helper);					
                                  
				  var id = this.getAttribute("id");
				  
                  if (this.addCard) {                    
                    game.promoteCard(cards[0], id);
                    
                  } else {
                    game.moveCards(cards, id);
                  
                  }
                  
                  $(cards).show();
                  helper.remove();

              }, pile, ui.draggable.nextAll('es-card').addBack().get(), helper);

              var spacingOffset = pile.flippedCards && pile.flippedCards().length == 0 ? 0 : pile.flippedSpacing || 0;

              helper.animate({
                  top: "+=" + (dropOffset.top - dragOffset.top + spacingOffset),
                  left: "+=" + (dropOffset.left - dragOffset.left)                
              }, 150, "swing", callback);
          }
            
          updateDraggables();
            
          $(game.stock).unbind('click').click(function() {
              game.drawCard();			
          });
        }
    };

    
  document.addEventListener('polymer-ready', function() {	
            
    $(function() {
        var game = new solitaire.Game();
		var hash = window.location.hash.replace('#','');
		
		var regex = /^\/klondike\/(\w+)$/;
		var match = regex.exec(hash);
				
		if (match) {
			game.loadGame(match[1]);
		
		} else {
			game.createGame();
		}

        var prompt = document.querySelector('es-prompt');
        
        prompt.addEventListener('confirm', function() {
            game.createGame();
            game.scorer.innerHTML = "0";
        });
        
        var menu = document.querySelector('es-menu');
        
        menu.addEventListener('new-game', function() {
            game.showMenu(); 
        });
	});
  });
  
})(wrap(document));
