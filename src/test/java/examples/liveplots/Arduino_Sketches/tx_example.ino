void setup() {
  //Se inicia la comunicacion serial
  Serial.begin(9600);
  //Se declara el pin 13 como salida de voltaje
  pinMode(13, OUTPUT);
}

void loop() {
  //Cuando haya datos disponibles para leer...
  if (Serial.available() > 0) {
    //Se leen los datos y se guardan en el String str
    String str = Serial.readString();
    //Si el mensaje recibido es "on"...
    if (str == "on") {
      //Se enciende el LED en el pin 13
      digitalWrite(13, HIGH);
      //Si el mensaje recibido es "off"...
    } else if (str == "off") {
      //Se apaga el LED en el pin 13
      digitalWrite(13, LOW);
    }
  }
}
