### Escuela Colombiana de Ingeniería
### Arquitecturas de Software – ARSW


## Laboratorio – Programación concurrente, condiciones de carrera y sincronización de hilos - Caso Inmortales

### Descripción
Este laboratorio tiene como fin que el estudiante conozca y aplique conceptos propios de la programación concurrente, además de estrategias que eviten condiciones de carrera.
### Dependencias:

* [Ejercicio Introducción al paralelismo - Hilos - BlackList Search](https://github.com/ARSW-ECI-beta/PARALLELISM-JAVA_THREADS-INTRODUCTION_BLACKLISTSEARCH)
#### Parte I – Antes de terminar la clase.

Control de hilos con wait/notify. Productor/consumidor.

1. Revise el funcionamiento del programa y ejecútelo. Mientras esto ocurren, ejecute jVisualVM y revise el consumo de CPU del proceso correspondiente. A qué se debe este consumo?, cual es la clase responsable?

**Luego de realizar el respectivo análisis del código, se demostró que la clase responsable del funcionamiento del programa es ```StartProduction()```, y al realizar la respectiva ejecución del programa y ejecutarlo mientras ejecutábamos Java VisualVM, se demostró un consumo de recursos excesivamente alto, al crear los hilos de ```Consumer``` y ```Producer``` y correrlos infinitamente sin control alguno usando un ciclo ```while(true)``` que se ejecuta infinitamente sin ninguna interrupción, con un porcentaje de consumo de CPU máximo de ```5.2%```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte1.PNG)

2. Haga los ajustes necesarios para que la solución use más eficientemente la CPU, teniendo en cuenta que -por ahora- la producción es lenta y el consumo es rápido. Verifique con JVisualVM que el consumo de CPU se reduzca.

**Para la realización de los ajustes necesarios para que la solución usara más eficientemente la CPU, se requirió el uso de varios bloqueos de la queue en los hilos de ```Producer``` y ```Costumer``` para poder reducir el desempeño de la CPU, como se puede observar en la imagen de Java VisualVM, la CPU tuvo un porcentaje de uso máximo de ```2.3%```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte1.2.PNG)

3. Haga que ahora el productor produzca muy rápido, y el consumidor consuma lento. Teniendo en cuenta que el productor conoce un límite de Stock (cuantos elementos debería tener, a lo sumo en la cola), haga que dicho límite se respete. Revise el API de la colección usada como cola para ver cómo garantizar que dicho límite no se supere. Verifique que, al poner un límite pequeño para el 'stock', no haya consumo alto de CPU ni errores.

**Ahora, se realizaron las respectivas modificaciones a la clase ```Producer``` y ```Consumer``` respectivamente, haciendo que el productor produzca muy rápido y el consumidor consuma lento. Asimismo se estableció el límite de stock en el valor original que es de ```5000```. Al ejecutar el programa simultáneamente con el Java VisualVM, el porcentaje de consumo de CPU fue de ```4.2%```. Inicialmente el consumo de CPU se debe unicamente al productor (en los primeros 5 segundos).**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte1.3.1.PNG)

**Luego se ejecutó el programa pero esta ves cambiando el límite de stock de ```5000``` a ```1000```, dejando la producción rápida. Como se puede observar en el Java VisualVM, el porcentaje de consumo de CPU fue muy similar (```4.0%```) a cuando el límite de stock era de ```5000```. Sumado a ello, no se generó ningún error.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte1.3.2.PNG)

**Por último, se ejecutó el programa cambiando el límite de stock de ```1000``` a ```10```, dejando la producción rápida. Luego de ejecutar el programa simultáneamente con Java VisualVM, el porcentaje de consumo de CPU fue de ```4.2%```, muy similar a los dos experimentos anteriores, en el cual tampoco se produjo ningún error.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte1.3.3.PNG)

#### Parte II. – Antes de terminar la clase.

Teniendo en cuenta los conceptos vistos de condición de carrera y sincronización, haga una nueva versión -más eficiente- del ejercicio anterior (el buscador de listas negras). En la versión actual, cada hilo se encarga de revisar el host en la totalidad del subconjunto de servidores que le corresponde, de manera que en conjunto se están explorando la totalidad de servidores. Teniendo esto en cuenta, haga que:

- La búsqueda distribuida se detenga (deje de buscar en las listas negras restantes) y retorne la respuesta apenas, en su conjunto, los hilos hayan detectado el número de ocurrencias requerido que determina si un host es confiable o no (_BLACK_LIST_ALARM_COUNT_).
- Lo anterior, garantizando que no se den condiciones de carrera.

**Para el desarrollo de este punto, agregamos una variable booleana en la clase ```HostBlackListsValidator``` que nos dice si ya había superado las ocurrencias o no.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/parte2.1.PNG)

**En el hilo modificamos la bandera y miramos que no haya superado el límite de ocurrencias para hacerlo, de ahí pues evaluamos que la bandera sea ```false``` para que se siga ejecutando, de lo contrario ya no será necesario.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/parte2.2.PNG)

#### Parte III. – Avance para la siguiente clase

Sincronización y Dead-Locks.

![](http://files.explosm.net/comics/Matt/Bummed-forever.png)

1. Revise el programa “highlander-simulator”, dispuesto en el paquete edu.eci.arsw.highlandersim. Este es un juego en el que:

	* Se tienen N jugadores inmortales.
	* Cada jugador conoce a los N-1 jugador restantes.
	* Cada jugador, permanentemente, ataca a algún otro inmortal. El que primero ataca le resta M puntos de vida a su contrincante, y aumenta en esta misma cantidad sus propios puntos de vida.
	* El juego podría nunca tener un único ganador. Lo más probable es que al final sólo queden dos, peleando indefinidamente quitando y sumando puntos de vida.

**El programa "highlander-simulator" ejecuta un juego en el que N jugadores inmortales se atacan entre sí permanentemente e indefinidamente, y al analizar la interfaz del programa, solo funciona el botón ```Start``` y ```Pause and check```, los cuales se encargan de inicializar el programa y pausar el programa respectivamente. Al pausar el programa, en la interfaz del programa se visualiza la vida de los tres inmortales y la sumatoria de la vida de los tres inmortales, como se ve en la siguiente imagen.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.1.PNG)

2. Revise el código e identifique cómo se implemento la funcionalidad antes indicada. Dada la intención del juego, un invariante debería ser que la sumatoria de los puntos de vida de todos los jugadores siempre sea el mismo (claro está, en un instante de tiempo en el que no esté en proceso una operación de incremento/reducción de tiempo). Para este caso, para N jugadores, cual debería ser este valor?.

**Al realizar la respectiva revisión de todas las clases, que son ```ControlFrame```, ```Immortal``` y ```ImmortalUpdateReportCallback```, se evidenció la vida de cada inmortal debería ser de 100 puntos, por lo tanto la vida global es igual a 100 multiplicado por la cantidad de N inmortales.**

3. Ejecute la aplicación y verifique cómo funcionan las opción ‘pause and check’. Se cumple el invariante?.

**Al realizar la respectiva ejecución del programa por primera ves, y al realizar clic en ```Pause and check``` por primera ves, obtenemos el siguiente resultado. Como se puede evidenciar, la sumatoria de la vida de los tres inmortales es de: ```480```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.2.1.PNG)

**Luego de realizar posteriormente un segundo clic en ```Pause and check```, sin terminar la ejecución del programa, se puede observar a continuación que la sumatoria de los tres inmortales es de: ```1480```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.2.2.PNG)

**Después de realizar el tercer clic en ```Pause and check```, sin terminar la ejecución del programa, se puede observar a continuación que la sumatoria de los tres inmortales es de: ```1990```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.2.3.PNG)

**Luego de realizar estos tres experimentos, claramente se evidencia que el invariante no se cumple, ya que la sumatoria de la vida global siempre está cambiando, como se ve en las tres imágenes anteriormente analizadas, cambia el valor de la sumatoria de la vida global de ```480``` a ```1480```, y de ```1480``` a ```1990``` respectivamente.**

4. Una primera hipótesis para que se presente la condición de carrera para dicha función (pause and check), es que el programa consulta la lista cuyos valores va a imprimir, a la vez que otros hilos modifican sus valores. Para corregir esto, haga lo que sea necesario para que efectivamente, antes de imprimir los resultados actuales, se pausen todos los demás hilos. Adicionalmente, implemente la opción ‘resume’.

**Antes de realizar la respectiva implementación en el código para que antes de imprimir los resultados actuales, se pausen todos los demás hilos, se analizó la clase ```Immortal```, la cual se escogió para realizar dicha implementación, añadiendo un condicional que lo que hace es que si el programa está en pausa, se pausen todos los hilos antes de imprimir los resultados actuales. Para ésto se modificó el método ```run()``` de la clase ```Immortal```, quedando de la siguiente forma.**

```java
public void run() {
		while (true) {
			if (!ControlFrame.stop) {
				if (!enPausa) {
					Immortal im;
					int myIndex = immortalsPopulation.indexOf(this);
					int nextFighterIndex = r.nextInt(immortalsPopulation.size());
					// avoid self-fight
					if (nextFighterIndex == myIndex) {
						nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
					}			
					im = immortalsPopulation.get(nextFighterIndex);
					this.fight(im);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					pausar();
				}
			}
		}
	}
```

**Posteriormente se realizó la respectiva implementación de la opción ```resume```, en la clase ```ControlFrame```, añadiendo el siguiente cambio al método ```actionPerformed```, quedando de la siguiente forma.**

```java
public void actionPerformed(ActionEvent e) {
                /**
                 * IMPLEMENTAR
                 */
            	synchronized (ControlFrame.getMonitor()) {
            		ControlFrame.getMonitor().notifyAll();
                	for (Immortal im : immortals) {
                    		im.setPausa(false);
                    	}
		} 
}
```

5. Verifique nuevamente el funcionamiento (haga clic muchas veces en el botón). Se cumple o no el invariante?.

**Luego de verificar nuevamente el funcionamiento realizando un clic, obtenemos el siguiente resultado. Como se observa en la imagen, la sumatoria de las vidas totales de los inmortales es de: ```280```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.5.1.PNG)

**Después, al realizar varias veces clic en el botón, el resultado de la sumatoria de vidas totales de los inmortales es de: ```120```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.5.2.PNG)

**Al realizar el experimento de realizar muchas veces clic en el botón, y luego de analizar los resultados, el invariante aún no se cumple por el momento, ya que a veces aumenta y a veces disminuye como se observa en las dos imágenes anteriormente analizadas.**

6. Identifique posibles regiones críticas en lo que respecta a la pelea de los inmortales. Implemente una estrategia de bloqueo que evite las condiciones de carrera. Recuerde que si usted requiere usar dos o más ‘locks’ simultáneamente, puede usar bloques sincronizados anidados:

	```java
	synchronized(locka){
		synchronized(lockb){
			…
		}
	}
	```

**Teniendo en cuenta las posibles regiones críticas en lo que respecta a la pelea de los inmortales, se le hicieron las siguientes modificaciones al código, añadiendo como dice en el enunciado bloques sincronizados anidados, en los cuales se implementó una estrategia de bloqueo que evite las condiciones de carrera, quedando el código de la siguiente forma.**

```java
public void fight(Immortal i2) {
		if (this.getId() < i2.getId()) {
			synchronized (i2) {
				synchronized (this) {
					callouts(i2);
				}

			}
		} else {
			synchronized (this) {
				synchronized (i2) {
					callouts(i2);
			}
		}
	}
}
```

7. Tras implementar su estrategia, ponga a correr su programa, y ponga atención a si éste se llega a detener. Si es así, use los programas jps y jstack para identificar por qué el programa se detuvo.

**Luego de correr el programa con tres inmortales, se detuvo automáticamente luego de ejecutarlo, el cual representa un problema, ya que no se cumple el invariante, al variar la sumatoria de vidas totales y al detenerse el programa automáticamente de la siguiente forma.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.7.PNG)

8. Plantee una estrategia para corregir el problema antes identificado (puede revisar de nuevo las páginas 206 y 207 de _Java Concurrency in Practice_).

**Luego de plantear una estrategia después de revisar el material del libro _Java Concurrency in Practice_, se tuvo que cambiar gran parte del código, principalmente los bloques sincronizados anidados propuestos en el numeral ```6```, quedando ahora el método ```fight``` de la clase ```Immortal``` de la siguiente forma.**

```java
public void fight(Immortal i2) {
    	if (i2.getHealth() > 0) {
            i2.changeHealth(i2.getHealth() - defaultDamageValue);
            this.health += defaultDamageValue;
            updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }
}
```

9. Una vez corregido el problema, rectifique que el programa siga funcionando de manera consistente cuando se ejecutan 100, 1000 o 10000 inmortales. Si en estos casos grandes se empieza a incumplir de nuevo el invariante, debe analizar lo realizado en el paso 4.

**Luego de analizar el problema usando los programas ```jps``` y ```jstack``` e identificando por qué el programa se detuvo, y realizando las respectivas correcciones, se observa que el programa ya funciona de manera consistente, retornando resultados con sumatorias de vida total válidos cuando se ejecutan 100, 1000 o 10000 inmortales, y demostrando ahora que el invariante si se cumple.**

**A continuación, se muestra la ejecución del programa con ```100``` inmortales. Como se observa a continuación, no hay resultados inconclusos, y el invariante en este caso, es la sumatoria de vidas totales, que como se muestra en la imagen, es de: ```10000```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.9.1.PNG)

**Ahora se muestra la ejecución del programa con ```1000``` inmortales. Como se observa a continuación, no hay resultados inconclusos, y el invariante en este caso, es la sumatoria de vidas totales, que como se muestra en la imagen, es de: ```100000```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.9.2.PNG)

**Por último, se muestra la ejecución del programa con ```10000``` inmortales. Como se observa a continuación, no hay resultados inconclusos, y el invariante en este caso, es la sumatoria de vidas totales, que como se muestra en la imagen, es de: ```1000000```.**

![img](https://github.com/Skullzo/ARSW-Lab3/blob/main/img/Parte3.9.3.PNG)

10. Un elemento molesto para la simulación es que en cierto punto de la misma hay pocos 'inmortales' vivos realizando peleas fallidas con 'inmortales' ya muertos. Es necesario ir suprimiendo los inmortales muertos de la simulación a medida que van muriendo. Para esto:
	* Analizando el esquema de funcionamiento de la simulación, esto podría crear una condición de carrera? Implemente la funcionalidad, ejecute la simulación y observe qué problema se presenta cuando hay muchos 'inmortales' en la misma. Escriba sus conclusiones al respecto en el archivo RESPUESTAS.txt.
	* Corrija el problema anterior __SIN hacer uso de sincronización__, pues volver secuencial el acceso a la lista compartida de inmortales haría extremadamente lenta la simulación.
	
**Para solucionar este problema se utilizó ```CopyOnWriteArrayList``` que trabaja principalmente con colección y concurrencia.**

**Posteriormente modificamos el método ```changeHealth```, donde fueron eliminados los inmortales que están muertos. El código quedó de la siguiente forma.**

```java
public void changeHealth(int v) {
	if(this.health > 0) {
		health = v;
	}else{
		health = v;
		immortalsPopulation.remove(this);
	}
}
```

11. Para finalizar, implemente la opción STOP.

**Para finalizar con el laboratorio, se analizó primero la clase ```ControlFrame```, en la cual se realizó una implementación de la opción ```STOP``` en el programa, verificando esa condicion de que el ```STOP``` sea falso siempre.**

```java
btnStop.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		for (Immortal im : immortals) {
			im.setPausa(true);
                }
                stop = true;
	}
});
```

## Autores
[Alejandro Toro Daza](https://github.com/Skullzo)

[David Fernando Rivera Vargas](https://github.com/DavidRiveraRvD)
## Licencia
Este contenido hace parte del curso Arquitecturas de Software del programa de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería, y está licenciado como <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a>.

<a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br />
