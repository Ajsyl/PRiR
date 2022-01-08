Programy przedstawiające różnice w wydajności obliczania fraktala Manderbrota oraz sumowaniu wektorów na CPU oraz GPU

Przy tworzeniu fraktali Malderbrota wykorzystany został język Python (dość wolny język) dzięki któremu przy wykorzystaniu odpowiednich bibliotek jesteśmy w stanie uruchomic program na GPU przez CUDA'e. Obraz wraz z obliczeniami na CPU został wykonany w ok 3s, zaś do tych samych obliczeń GPU potrzebowało ok 50ms czyli jakieś 60 razy szybciej niż na procesorze. 

Przy sumowaniu wektorów korzystając z języka C++ i CUDA dla 500000 iteracji program wykonał się zaledwie 2 krotnie szybciej na GPU niż CPU. Może to być przyczyną zbyt małej ilości iteracji(większa ilość wymagała by inicjalizacji statycznej), długiego czasu przesyłu danych między pamięcią lub złej interpretacji lub złego przykładu.   