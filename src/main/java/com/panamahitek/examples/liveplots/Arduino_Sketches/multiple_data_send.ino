int i = 0;
int j = 10;
int k = 100;
int l = 1000;
void setup() {
  Serial.begin(9600);
}

void loop() {
  /*
    Remplazar el valor de las variables "i", "j", "k" y "l" por los sensores
    cuyo valor se quiere enviar a Java para que sea graficado
  */
  Serial.println(i);
  Serial.println(j);
  Serial.println(k);
  Serial.println(l);
  i++;
  j++;
  k++;
  l++;
  delay(1000);
}

