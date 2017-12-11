int i = 0;
int j = 10;
void setup() {
  Serial.begin(9600);
}

void loop() {
  /*
    Remplazar el valor de las variables "i" y "j" por los sensores
    cuyo valor se quiere enviar a Java para que sea graficado
  */
  Serial.println(i);
  Serial.println(j);
  i++;
  j++;
  delay(1000);
}

