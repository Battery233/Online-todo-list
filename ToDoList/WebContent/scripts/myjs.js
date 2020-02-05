$("input[type='text']").on("keypress", function(){
	if(event.which == 13){
		if ($(this).val() === '') {
			alert("Please write something!");
        } else {
        	$("ul").append("<li>" + "      " + $(this).val() + "      " + "<span><i class='fa fa-trash-o'></i></span></li>");
        	var user_id = "1111";
        	var content = $(this).val();
        	var req = JSON.stringify({
        	      user_id: user_id,
        	      content: content
        	    });
        	var url = './main';
    		var params = 'user_id=' + user_id;
    		fetch(url + '?' + params, {
    			method: 'POST',
    			body: req
    		}).then(function(response) {
    			return response.json;
    		})
        }
		
		$(this).val("");
	}
});
$(document).on("click", "li", function(){
	$(this).toggleClass("done");
});

$(document).on("click", "li span", function(){
	$(this).parent().fadeOut(function(){
		var url = './main';
		var user_id = "1111";
		var params = 'user_id=' + user_id;
		var item_id = $(this).attr("data-item_id");
    	var content = $(this).val();
    	var req = JSON.stringify({
    		  item_id: item_id,
    	      user_id: user_id,
    	      content: content
    	    });
		fetch(url + '?' + params, {
			method: 'DELETE',
			body: req
		}).then(function(response) {
			return response.json;
		})
		$(this).remove();
	});
})
$("h1 i").click(function(){
	$(".slide").slideToggle();
});

(function() {
	var user_id = '1111';
	
	function $create(tag, options) {
		  var element = document.createElement(tag);
		  for (var key in options) {
		    if (options.hasOwnProperty(key)) {
		      element[key] = options[key];
	        }
		  }
		    return element;
	  }
	
	function showWarningMessage(msg) {
	    var itemList = document.querySelector('#item-list');
	    itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-triangle"></i> ' +
	      msg + '</p>';
	  }

	function showErrorMessage(msg) {
		  var itemList = document.querySelector('#item-list');
		  itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-circle"></i> ' +
		    msg + '</p>';
	   }
	
	function loadItems() {
		var url = './main';
		var params = 'user_id=' + user_id;
		var data = null;
		fetch(url + '?' + params, {
			method: 'GET',
		})
		.then(res => {
			return res.json();
			
		})
		.then(items => {
			console.log(items);
			if (!items || items.length === 0) {
				showWarningMessage('No to-do items.');
			} else {
				listItems(items);
			}
		})
		.catch(err => {
			console.error(err);
		})
	}
	
	function listItems(items) {
	    var itemList = document.querySelector('#item-list');
	    itemList.innerHTML = ''; // clear current results

	    for (var i = 0; i < items.length; i++) {
	      addItem(itemList, items[i]);
	    }
	  }
	
	function addItem(itemList, item) {
		var item_id = item.item_id;
		var li = $create('li', {
		      id: 'item-' + item_id,
		      className: 'item'
		    });
		
		li.dataset.item_id = item_id;
	    
	    var section = $create('span');
	    var trash = $create('i', {
	        className: 'fa fa-trash-o'
	      });
	    section.appendChild(trash);
	    li.innerHTML = "      " + item.content + "      ";
	    li.appendChild(section);
	    itemList.appendChild(li);
	}
	loadItems();
}) ();