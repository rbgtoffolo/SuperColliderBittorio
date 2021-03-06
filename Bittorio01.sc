/* Bittorio experimento 01


Bittorio01.new(numeroDeCelulas, regraEmInteiro de 0 a 255);

**** VARIÁVEIS DA INSTÂNCIA ****

nCells = Quantidade de células no bittorio
ruleInt = Integer que representa a regra
rule = representação binária das regras de cálculo das gerações.
cells = Array com nCells posições que armazena os estados das células (0 ou 1) para a síntese das frequências


cells:
[ 0, 1, 0, 0, 0, 1 ...] = frequência ligada ou desligada em um range/resolução de frequências a ser determinada pela quantidade de células. Da mesma forma que os BINS de uma análise de FFT.


**** REGRAS ****

Cada posição do array rule equivale ao novo estado da célula quando vizinhas esquerda, célula em questão, vizinha direita forem:
1 1 1 = rule[0]
1 1 0 = rule[1]
1 0 1 = rule[2]
1 0 0 = rule[3]
0 1 1 = rule[4]
0 1 0 = rule[5]
0 0 1 = rule[6]
0 0 0 = rule[7]

Nas duas primeiras e últimas células da ponto o cálculo é feito usando a outra ponta do array de modo a considerarmos o bittório como uma entidade circular fechada.


*/

Bittorio01 {
	var <>nCells = 1024, <>ruleInt = 30,  <rule,  <cells;

	*new { | numberOfCells, ruleInteger = 30 |
		^super.new.init(numberOfCells, ruleInteger);
	}


	// inicia variáveis da instância e cria arrays cells e ampBittorio com distribuição randômica de 0s e 1s.

	init { | numberOfCells, ruleInteger |

		if ( (ruleInteger < 0) || (ruleInteger > 255),
			{ "Rule must be between 0 and 255".throw;
				^this},
			{}
		);

		nCells = numberOfCells;
		cells = Array.fill(nCells, { [0,1].choose });
		ruleInt = ruleInteger;
		rule = ruleInt.asBinaryDigits;
	}

		prState { | left, cell, right |
		var result;
		result = case
		{ left == 1 && cell == 1 && right == 1 }
		{ result = rule.at(0) }

		{ left == 1 && cell == 1 && right == 0 }
		{ result = rule.at(1) }

		{ left == 1 && cell == 0 && right == 1 }
		{ result = rule.at(2) }

		{ left == 1 && cell == 0 && right == 0 }
		{ result = rule.at(3) }

		{ left == 0 && cell == 1 && right == 1 }
		{ result = rule.at(4) }

		{ left == 0 && cell == 1 && right == 0 }
		{ result = rule.at(5) }

		{ left == 0 && cell == 0 && right == 1 }
		{ result = rule.at(6) }

		{ left == 0 && cell == 0 && right == 0 }
		{ result = rule.at(7) };
		^result;
	}

	calcNextGen {
		var nextGeneration = Array.new(nCells);
		cells.do({ | cell, count |
			var result;
			result = case
			{ count == 0 }
			{ var left, right;
				left = cells.last;
				right = cells.at(count+1);
				//postf("left: % - cell: %  - right: % \n", left, cell, right);
				result = this.prState(left, cell, right);
			}
			{ count == (cells.size-1) }
			{ var left, right;
				left = cells.at(count-1);
				right = cells.first;
				//postf("left: % - cell: %  - right: % \n", left, cell, right);
				result = this.prState(left, cell, right);
			}
			{ (count != 0) && (count != (cells.size-1)) }
			{ var left, right;
				left = cells.at(count-1);
				right = cells.at(count+1);
				//postf("left: % - cell: %  - right: % \n", left, cell, right);
				result = this.prState(left, cell, right);
			};

			nextGeneration.add(result);
		});
		cells = nextGeneration;
		^cells;
	}

	// Método para pegar os estados do bittorio de n e n (bitSize) itens e transformar em decimal.

	groupDecimal { | bitSize |
		var tempArray = Array.new(cells.size);
		(cells.size/bitSize).do({ | count |
			var temp, pos;
			pos = bitSize * count;
			temp = 0;
			bitSize.do({ arg i;
				var decimal = 0;
				decimal = cells.at(pos+i) * 2.pow((bitSize-1)-i);
				temp = temp + decimal;
			});
			tempArray.add(temp);
		});
		^tempArray;
	}
}


