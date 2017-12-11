void setup() {
  //Se inicia la comunicacion serial
  Serial.begin(9600);
}

void loop() {
  //Cuando haya datos disponibles para leer...
  if (Serial.available() > 0) {
    //Se leen los datos y se guardan en el String str
    String str = Serial.readString();
    //Se transforma str a double y se calcula su raiz cuadrada
    double root = sqrt(str.toDouble());
    //Se imprime el resultado de la raiz cuadrada en el puerto serie
    Serial.println(root);
  }
}
