import 'dart:typed_data';
import 'package:http/http.dart';
import '../models/photos.dart';
import '../models/state.dart';
import 'dart:convert';

class ImageProvider {
  //Singleton
  static final ImageProvider _imageProvider = ImageProvider._private();
  ImageProvider._private();
  factory ImageProvider() => _imageProvider;

  final Client _client = Client();

  static const String _apiKey =
      "d6904559e557b57caf1ec6f199915f2c13643da2f6def4f35f3b1684cb3ceba7";

  static const String _baseUrl = "https://api.unsplash.com";

  //Get list of images based on the query
  Future<State> getImagesByName(String query) async {
    Response response;
    if (_apiKey == 'api-key') {
      return State<String>.error("Please enter your API Key");
    }
    response = await _client.get(Uri.parse(
        "$_baseUrl/search/photos?page=1&query=$query&client_id=$_apiKey"));
    if (response.statusCode == 200) {
      return State<Photos>.success(Photos.fromJson(json.decode(response.body)));
    } else {
      return State<String>.error(response.statusCode.toString());
    }
  }

  Future<Uint8List> getImageFromUrl(String url) async {
    var response = await _client.get(Uri.parse(url));
    Uint8List bytes = response.bodyBytes;
    return bytes;
  }
}
