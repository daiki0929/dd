var selected = [];
// Namespace
var PRJ = PRJ || {};

(function($, d, ns){
	'use strict';

	function pad2(arg) {
		var tmp = '00' + arg;

		return tmp.slice(-2, tmp.length);
	}

	var pochipochi = {
			c: {
				done: '<a href="javascript:;" id="done" onclick="setNoReserveDate();">決定</a>'
			},
			initialize: function() {
				this.$elm = $('#pochipochi');
				this.$elm.datepicker({
					onSelect: $.proxy(this.handleSelect, this),
					beforeShowDay: $.proxy(this.beforeShowDay, this),
					dateFormat: 'yy/mm/dd'
				}).hide();
				this.$done = $(this.c.done);
				this.$done.click($.proxy(this.handleDone, this));
				this.$elm.append(this.$done);
				this.$btn = $('#button');
				this.$btn.click($.proxy(this.handleClick, this));
			},
			handleClick: function() {
				this.$elm.show();
			},
			handleDone: function() {
				this.$elm.hide();
			},
			handleSelect: function(date) {
				var index = $.inArray(date, this.selected);
				if(index == -1) {
					selected.push(date);
				} else {
					delete selected[index];
				}
			},
			beforeShowDay: function(date) {
				var theday = date.getFullYear() + '/' +
				pad2(date.getMonth()+1)+'/'+
				pad2(date.getDate());

				return [true, $.inArray(theday, selected)>=0? 'selected':''];
			}
	};

	// Export
	ns.pochipochi = pochipochi;
})(jQuery, document, PRJ);

$(function() {
	PRJ.pochipochi.initialize();
});

function setNoReserveDate(){
	$('[name=noReserveDate]').attr("value", selected);
	console.log($('[name=noReserveDate]').val());
}