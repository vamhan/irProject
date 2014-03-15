$.fn.hoverEffect = function() {
	var selector = this;
	
	this.each(function() {
		var obj = $(this); 
		var hObj = $(this).find('div.hover');
		
		hObj.animate({opacity: 0},0);
		
		obj.hover(function() {
			hObj.stop().animate({opacity: 1},250);
		},function() {
			hObj.stop().animate({opacity: 0},250);
		});
		
	});
};
