/**

Para futura implementação do Bittorio que vai ser usado para controlar amplitudes.


ampBittorio = Array com 4*nCells posições para armazenar as amplitudes para cada frequência em freqBittorio.
a cada 4 células formamos um valor de 4 bits (0 a 16) para converter em valor de amplitude.


ampBittorio
[ 0, 1, 1, 0,      0, 0, 0, 1,
amp1 = 5         amp2 = 1 ....

valores de amplitudes serão escalonados com .linlin(0,15,0.0,1.0);

**/