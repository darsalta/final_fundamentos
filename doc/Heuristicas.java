/***** Heurística BL, trata de colocar pieza en objeto
	   Regresa:  true: si logra colocar pieza;  false: si no lo logra  */
	private boolean BLHeuristic(Objeto objeto, Pieza pieza)
	{
		//Coloca la pieza en la parte superior derecha del objeto (justo afuera del objeto)
		int despX, despY;
		despX = objeto.getXmax() - pieza.getXmax();
		moveDistance(despX, 4);
		despY = objeto.getYmax() - pieza.getYmin();
		moveDistance(despY, 1);
		
		runIntoBottomLeftPlacement(objeto, pieza);  
	   	    
		if( posicionValida(objeto, pieza) )   
		{
			return true;
		}
		return false;
	}

	
	public void moveDistance(int dist, int dir ){
		switch(dir)
		{
			//Up
			case 1:
				for(int i=0; i<vertices; i++)
				{
					coordY[i] += dist;
				}
				break;
			//Down
			case 2: 
				for(int i=0; i<vertices; i++)
				{
					coordY[i] -= dist;
				}
				break;
			//Left
			case 3:
				for(int i=0; i<vertices; i++)
				{
					coordX[i] -= dist;
				}
				break;
			//Right
			case 4:
				for(int i=0; i<vertices; i++)
				{
					coordX[i] += dist;
				}
				break;
		}
	}	
	
	
	
   /***  Mueve la pza hasta una posicion estable lo mas abajo y a la izquierda posible
	    devuelve TRUE si hubo movimiento y FALSE si no hubo.*/
	private boolean runIntoBottomLeftPlacement(Objeto objeto, Pieza pieza)
	{
		int distVertical;
		int distHorizontal;
		int xpos = pieza.getXmin();
		int ypos = pieza.getYmin();
		int numgrande = 100000;  //es el valor que devuelven los métodos de cercanía cuando una pieza no alcanza a la otra.

		do 
		{

			distVertical = cercaniaVerOP(objeto, pieza);  //Distancia hacia abajo q puede moverse la pza hasta topar.
			if(distVertical > 0 && distVertical < numgrande)
			{
				pieza.moveDistance(distVertical, 2);  //2 = Down
			}

			distHorizontal = cercaniaHorOP(objeto, pieza); //Distancia hacia la izq q puede moverse la pza hasta topar.
			if(distHorizontal > 0 && distHorizontal < numgrande) 
			{
				pieza.moveDistance(distHorizontal, 3);  //3 = Left
			}

		}while( (distHorizontal > 0 && distHorizontal < numgrande)  
				|| (distVertical > 0 && distVertical < numgrande)  );   

		if (xpos == pieza.getXmin() && ypos == pieza.getYmin())
		{    // si no seacomodo la pieza
			return false;
		}
		return true;
	}


/***Dado un objeto, indica si las coordenadas de 
	la pieza son válidas para colocarse dentro de él.*/
	private static boolean posicionValida(Objeto objeto, Pieza pieza)
	{
		if(pieza.getYmax() <= objeto.getYmax() && 
		   pieza.getXmax() <= objeto.getXmax() && 
		   pieza.getXmin()>=0 && pieza.getYmin()>=0 ) //la pieza no se sale de los límites del objeto.
			{  
				if( interseccionOP(objeto, pieza) == false)  //la pieza no tiene intersección con ninguna otra del objeto.
				{  
					return true;
				}
			}

		return false;
	}



/********  Implementa DJD. */
public static void DJD(List<Pieza> listapiezas, List<Objeto> listaObjetos, int xObjeto, int yObjeto, double CapInicial)
	{ 	
		boolean acomodopieza = false; 
		int incremento = ((Objeto)listaObjetos.get(0)).gettotalsize() / 20;  // el desperdicio se incrementa en 1/20 del objeto.
		int w = 0; //desperdicio
		listapiezas = OrdenaPiezas(listapiezas, 1);  //de mayor a menor
		boolean terminar = false;  // decide cuándo pasar a otro objeto.

		//Revisa objetos con menos de CapInicial para meter una sola pieza.
		//En alguna HH podría ser necesario revisar varios objetos
		for (int j = listaObjetos.size()-1; j < listaObjetos.size(); j++)  
		{
			nextObjeto = (Objeto)listaObjetos.get(j);
			if(nextObjeto.getUsedArea() < nextObjeto.gettotalsize()*CapInicial)   // CapInicial = 1/4 o 1/3
			{
				for (int i=0; i<listapiezas.size(); i++)   //de mayor a menor
				{
					pza = (Pieza)listapiezas.get(i);
					if (pza.getTotalSize() <= nextObjeto.getFreeArea() )
					{
						acomodopieza = BLHeuristic(nextObjeto, pza);  //true o false, dependiendo se se puede acomodar la pieza.
						if (acomodopieza)
						{
							nextObjeto.addPieza(pza);
							listapiezas.remove(pza);
							return;
						}
					}
				}
			}
		}

		// No hubo objetos con menos de 1/3 de capacidad (o bien, ninguna pieza cupo en 
		// un objeto con menos de 1/3 de capacidad, lo que podría ocurrir).
		for (int j = listaObjetos.size()-1; j < listaObjetos.size(); j++) //En alguna HH podría ser necesario  
																		  //revisar varios objetos
		{
			nextObjeto = (Objeto)listaObjetos.get(j);
			w = 0;
			terminar = false;

			if( verificador(listapiezas, nextObjeto.getFreeArea()) )
			{
				continue;  //si por area libre, ya no cabe ninguna pieza, se pasa al otro objeto, en caso de haber.
			}

			do
			{
				if(	unapieza(listapiezas, nextObjeto, w) )
				{
					return;
				}
				if(listapiezas.size()>1  && dospiezas(listapiezas, nextObjeto, w))
				{
					return;
				}
				if(listapiezas.size()>2  && trespiezas(listapiezas, nextObjeto, w))
				{
					return;
				}

				if(w > nextObjeto.getFreeArea() )
				{
					terminar = true;
				}
				w+= incremento;  

			}while(!terminar);
		}


		nextObjeto=abreNuevoObjeto(listaObjetos, xObjeto, yObjeto);
		pza = SearchGreatest(listapiezas);
		acomodopieza = BLHeuristic(nextObjeto, pza);
		if (acomodopieza)  //el objeto es nuevo, siempre debería poder acomodar la pza.
		{
			nextObjeto.addPieza(pza);
			listapiezas.remove(pza);
		}

	}
	
	
	private static boolean verificador(List<Pieza> listapiezas1, int freearea)
	{
		Pieza pza1;
		for(int i=listapiezas1.size()-1; i>=0; i--)  //como se entrega la lista ordenada de mayor a menor,
		{											 //si se empieza a buscar desde el último (pza + chica)
			pza1 = (Pieza)listapiezas1.get(i);		 //devuelve un 'false' con menos comparaciones.
			if(pza1.getTotalSize() <= freearea)
			{
				return false;   
			}		
		}
		return true;
	}
	
	
	
/** Indica si puede o no poner una pieza en el objeto, dejando un máximo de desperdicio w.
    SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR */
	private boolean unapieza(List<Pieza> listapiezas1, Objeto nextObjeto1, int w)    
	{
		Pieza pza1;
		boolean acomodo = false;
		int arealibre;
		arealibre = nextObjeto1.getFreeArea();

		for(int i=0; i<listapiezas1.size(); i++)
		{
			pza1 = (Pieza)listapiezas1.get(i);
			if( (arealibre-pza1.getTotalSize()) > w )
			{
				break;  // si con una pieza deja más desperdicio que w, con las demás también lo hará (dado q están ordenadas)
			}

			acomodo = BLHeuristic(nextObjeto1, pza1);
			if (acomodo)
			{
				nextObjeto1.addPieza(pza1);
				listapiezas1.remove(pza1);
				return = true; // indica que ya acomodó pieza.
			}
		}
		return = false;
	}

	
	
	/** Indica si puede o no poner dos piezas en el objeto, dejando un máximo de desperdicio w.
    SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR */
	private static boolean dospiezas(List<Pieza> listapiezas1, Objeto nextObjeto1, int w1)
	{
		Pieza pza1, pza2;
		boolean acomodo1 = false, acomodo2 = false;
		int area0, area1;  //guardará el área de las 2 piezas más grandes.
		int areaU;		   //guardará el área de la pieza más pequeña.
		int arealibre;
		pza1 = (Pieza)listapiezas1.get(listapiezas1.size()-1);  
		areaU = pza1.getTotalSize();
		arealibre = nextObjeto1.getFreeArea();
		
		//verificando si cabrían 2 piezas con ese desperdicio máximo permitido.
		// SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR  (se revisan las 2 piezas + grandes).
		pza1 = (Pieza)listapiezas1.get(0);
		pza2 = (Pieza)listapiezas1.get(1);
		area0 = pza1.getTotalSize();
		area1 = pza2.getTotalSize();
		if( (arealibre-area0-area1) > w1)
		{
			return = false;
		}

		for(int i=0; i<listapiezas1.size(); i++)
		{
			acomodo1 = false;
			acomodo2 = false;
			pza1 = (Pieza)listapiezas1.get(i);

			if(arealibre - pza1.getTotalSize()-area0 > w1)
			{
				break;  // con pza1 y la más grande dejan más w, ya no tiene caso probar + pzas1.
			}

			if(pza1.getTotalSize()+areaU > arealibre )  
			{
				continue;  	// a la sig. pza 1. Pza1 + la mas chica se pasarían del área disponible.
			}				

			acomodo1 = BLHeuristic(nextObjeto1, pza1);
			if (acomodo1)
			{
				nextObjeto1.addPreliminarPieza(pza1);  //se añade pza como 'borrador'
				//no altera el FreeArea de objeto.

				// si puede acomodar pza1, prueba con cuál pza2 entra simultáneamente.
				for(int j=0; j<listapiezas1.size(); j++)
				{
					pza2 = (Pieza)listapiezas1.get(j);

					if ( (arealibre-pza1.getTotalSize()-pza2.getTotalSize()) >  w1)
					{
						break;  // si con pza2 elegida se deja + w, con las sig. pzas2 también lo haría.
					}
					
					if ( (pza1.getTotalSize() + pza2.getTotalSize()) > arealibre || i==j)  
					{	
						continue;   // a la sig. pza 2.
					}

					acomodo2 = BLHeuristic(nextObjeto1, pza2);
					if (acomodo2)
					{
						nextObjeto1.removePreliminarPieza(pza1);  //se borra el pegado preliminar.
						nextObjeto1.addPieza(pza1);  // se añade definitivamente.
						nextObjeto1.addPieza(pza2);
						listapiezas1.remove(pza1);
						listapiezas1.remove(pza2);
						return = true;  // indica que ya acomodó 2 piezas.
					}
				} //termina de revisar posibles pzas 2.

				nextObjeto1.removePreliminarPieza(pza1);    //Ninguna pza2 entró con la posible pza1.  
				//Se borra el preliminar de pza1
			}
		}  //termina de revisar posibles pzas 1.

		return = false;
	}


	
	/** Indica si puede o no poner tres piezas en el objeto, dejando un máximo de desperdicio w.
    SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR */
	private static void trespiezas(List<Pieza> listapiezas1, Objeto nextObjeto1, int w1)
	{
		Pieza pza1, pza2, pza3;
		boolean acomodo1 = false, acomodo2 = false, acomodo3 = false;
		int area0, area1, area2;  //guardará el área de las 3 piezas más grandes.
		int areaU1, areaU2;		  //guardará el área de las 2 piezas más pequeñas.
		int arealibre;
		pza1 = (Pieza)listapiezas1.get(listapiezas1.size()-1);  
		pza2 = (Pieza)listapiezas1.get(listapiezas1.size()-2);  
		areaU1 = pza1.getTotalSize();
		areaU2 = pza2.getTotalSize();
		arealibre = nextObjeto1.getFreeArea();

		//verificando si cabrían 3 piezas con ese desperdicio máximo permitido.
		// SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las piezas + grandes).
		pza1 = (Pieza)listapiezas1.get(0);
		pza2 = (Pieza)listapiezas1.get(1);
		pza3 = (Pieza)listapiezas1.get(2);
		area0 = pza1.getTotalSize();
		area1 = pza2.getTotalSize();
		area2 = pza3.getTotalSize();
		if( (arealibre-area0-area1-area2) > w1)
		{
			return = false;
		}

		for(int i=0; i<listapiezas1.size(); i++)
		{
			acomodo1 = false;
			acomodo2 = false;
			acomodo3 = false;

			pza1 = (Pieza)listapiezas1.get(i);
			if(arealibre-pza1.getTotalSize()-area0-area1 > w1)
			{
				break;  // esa pza 1 no es 'compatible' con ningun otro par de piezas
			}			// sin pasarse del desperdicio máximo permitido.
			if(pza1.getTotalSize() +areaU1 + areaU2> arealibre )
			{
				continue;  	// a la sig. pza 1.  Pza1 + las2+chicas se pasarían del área libre.
			}				

			acomodo1 = (nextObjeto1, pza1, H_acomodo1);
			if (acomodo1)
			{
				nextObjeto1.addPreliminarPieza(pza1);  //se añade pza1 como 'borrador'
				//no altera el FreeArea de objeto.
				// si puede acomodar pza1, prueba con cuál pza2 entra simultáneamente.
				for(int j=0; j<listapiezas1.size(); j++)
				{
					pza2 = (Pieza)listapiezas1.get(j);

					if(arealibre-pza1.getTotalSize()-pza2.getTotalSize()-area0 > w1)
					{
						break;  // las pzas 1-2 no son 'compatibles' con ninguna otra pieza
					}			// sin pasarse del desperdicio máximo permitido.

					if ( (pza1.getTotalSize() + pza2.getTotalSize()+areaU1) > arealibre || i == j)
					{      
						continue;   // a la sig. pza 2.  Pza1+Pza2+MásChica se pasarían el área libre.
					}

					acomodo2 = BLHeuristic(nextObjeto1, pza21);
					if (acomodo2)
					{
						nextObjeto1.addPreliminarPieza(pza2);  

						for(int k =0; k<listapiezas1.size(); k++)
						{
							pza3 = (Pieza)listapiezas1.get(k);

							if ( (arealibre-pza1.getTotalSize()-pza2.getTotalSize()-pza3.getTotalSize()) > w1)
							{
								break;  // si con pza3 elegida se deja + w, con las sig. pzas3 (más chicas) también lo haría.
							}			// deja de revisar pzas3 y se pasa a la sig. pza2.

							if ( (pza1.getTotalSize()+pza2.getTotalSize()+pza3.getTotalSize()) > arealibre 
									 || i == k || j == k   ) //Misma advertencia que en función 'dospiezas'
							{   								
								continue;   // a la sig. pza 3.
							}

							acomodo3 = BLHeuristic(nextObjeto1, pza3);
							if (acomodo3)
							{
								nextObjeto1.removePreliminarPieza(pza1);  //se borra el pegado preliminar.
								nextObjeto1.removePreliminarPieza(pza2);
								nextObjeto1.addPieza(pza1);  // se añaden definitivamente.
								nextObjeto1.addPieza(pza2);
								nextObjeto1.addPieza(pza3);
								listapiezas1.remove(pza1);
								listapiezas1.remove(pza2);
								listapiezas1.remove(pza3);
								return = true;  // indica que ya acomodó 3 piezas.
							}

						} //termina de revisar posibles pzas 3.

						nextObjeto1.removePreliminarPieza(pza2);    //Ninguna pza3 entró con la posible pza1y2.  
					}

				} //termina de revisar posibles pzas 2.

				nextObjeto1.removePreliminarPieza(pza1);    //Ningun par de pzas 2y3 entró con la posible pza1.  
				//Se borra el preliminar de pza1
			}
		}  //termina de revisar posibles pzas 1.

		return = false;
	}


		