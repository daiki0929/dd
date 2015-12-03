var map;
var latlng;
var marker;
//var subMarker;
var firstMapLat;
var firstMapLng;
var firstMapSize;
var firstMarkerLat;
var firstMarkerLng;
//var firstSubMarkerLat;
//var firstSubMarkerLng;

/**
 * MAP初期化
 */
function initializeMap() {
	//初期表示状態
	firstMapLat = parseFloat($('#mapLat').val());
	firstMapLng = parseFloat($('#mapLng').val());
	firstMapSize = parseInt($('#mapSize').val());
	latlng = new google.maps.LatLng(firstMapLat, firstMapLng);
	var myOptions = {
			zoom: firstMapSize,
			center: latlng,
			mapTypeControl: false,
			streetViewControl:false,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			scrollwheel: false
		};

	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		
	firstMarkerLat = parseFloat($('#markerLat').val());
	firstMarkerLng = parseFloat($('#markerLng').val());
//	firstSubMarkerLat = parseFloat($('#subMarkerLat').val());
//	firstSubMarkerLng = parseFloat($('#subMarkerLng').val());
	//サブマーカーの保存内容のロード
	marker = new google.maps.Marker({
						position: new google.maps.LatLng(firstMarkerLat, firstMarkerLng),
							map: map,
							title: 'マーカーのタイトル',
							draggable:true,
						icon:"http://maps.google.co.jp/mapfiles/ms/icons/pink.png"
					});

	//GoogleMap検索用
	$('#mapSearch').click(function() {
		var sad = $('#address').val();
		var geocoder = new google.maps.Geocoder();
		geocoder.geocode({ 'address': sad}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				map.setCenter(results[0].geometry.location);
				marker.setPosition(results[0].geometry.location);
				var p = marker.position;
				$('#lat').val(p.lat());
				$('#lng').val(p.lng());
			} else {
				alert('住所から場所を特定できませんでした。');
			}
		});
		getMarker();
		return false;
	});
	
	$('#resetMarder').click(function() {
		getReMarker();
	});
}

/**
 * POST時にMAP情報をHIDDENにセット
 */
function setMapInfo(){
	var centerPosition =  new google.maps.LatLng();
	centerPosition = map.getCenter();
	// 地図の中心座標の緯度経度
	var centerPosition =  new google.maps.LatLng();
	centerPosition = map.getCenter();
	$('#mapLat').val(centerPosition.lat());
	$('#mapLng').val(centerPosition.lng());
	$('#mapSize').val(map.getZoom());

	// マーカーの緯度
	if(marker.getVisible()){
		$('#markerLat').val(marker.getPosition().lat());
		$('#markerLng').val(marker.getPosition().lng());
	}else{
		$('#markerLat').val(null);
		$('#markerLng').val(null);
	}

	// サブマーカーの緯度
//	if(subMarker.getVisible()){
//		$('#subMarkerLat').val(subMarker.getPosition().lat());
//		$('#subMarkerLng').val(subMarker.getPosition().lng());
//	}else{
//		$('#subMarkerLat').val(null);
//		$('#subMarkerLng').val(null);
//	}
}

/**
 * POST時に描画時のMAP情報をHIDDENにセット
 */
function setFirstMapInfo(){
	var centerPosition =  new google.maps.LatLng();
	centerPosition = map.getCenter();

	$('#mapLat').val(firstMapLat);
	$('#mapLng').val(firstMapLng);
	$('#mapSize').val(firstMapSize);

	$('#markerLat').val(firstMarkerLat);
	$('#markerLng').val(firstMarkerLng);

//	$('#subMarkerLat').val(firstSubMarkerLat);
//	$('#subMarkerLng').val(firstSubMarkerLng);
}

// --------------------------------
// 選択中の中心座標を取得する。
// --------------------------------
function getCenterPostion() {
	if(marker.getVisible()){
		alert('表示');
	}else{
		alert('非表示');
	}

	alert('マーカーの緯度' + marker.getPosition().lat() + ' 経度' + marker.getPosition().lng());
}

// --------------------------------
// マーカーを再作成
// --------------------------------
function getReMarker() {
	var centerPosition =  new google.maps.LatLng();
	centerPosition = map.getCenter();

	//直前のマーカーを破棄
	marker.setMap(null);
	marker = new google.maps.Marker({
						position: new google.maps.LatLng(centerPosition.lat(),centerPosition.lng()),
					    map: map,
					    draggable:true,
						icon:"http://maps.google.co.jp/mapfiles/ms/icons/pink.png"
					});
}

// --------------------------------
// マーカーを追加
// --------------------------------
function getMarker() {
	var centerPosition =  new google.maps.LatLng();
	centerPosition = map.getCenter();

	marker.setMap(null);
	marker = new google.maps.Marker({
						position: new google.maps.LatLng(centerPosition.lat(),centerPosition.lng()),
					    map: map,
					    draggable:true,
						icon:"http://maps.google.co.jp/mapfiles/ms/icons/pink.png"
					});
}

// --------------------------------
// マーカーを削除
// --------------------------------
function deleteMarker() {
	if(marker){
//		marker.setMap(null);
		marker.setVisible(false);
	}
//	if(subMarker){
//		subMarker.setMap(null);
//		subMarker.setVisible(false);
//	}
}
