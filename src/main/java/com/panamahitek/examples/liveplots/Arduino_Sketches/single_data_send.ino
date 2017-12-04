int i = 0;
void setup() {
  Serial.begin(9600);
}

void loop() {
  /*
    Remplazar el valor de la variable "i" por el del sensor cuyo valor
    se quiere enviar a Java para que sea graficado
  */
  Serial.println(i);
  i++;
  delay(1000);
}

