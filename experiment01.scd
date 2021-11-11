(
SynthDef.new(\synthBittorio, { arg freq = 400, amp = 0.1, pan = 0, atk = 0.1, sus = 5, rel = 3, gate = 1;
	var sig, env;
	//env = EnvGen.kr(Env.asr(atk, sus, rel), gate, doneAction:2);

	sig = SinOsc.ar(freq, Rand(0,6));
	sig = Pan2.ar(sig, pan);
	sig = sig * amp;
	Out.ar(0, sig);
}).add;
)

(

var freqBittorio, ampBittorio, synthBittorio, mainRoutine,
    minFreq = 100, maxFreq = 4000, numCells = 100, freqRatio, ampBitSize = 4, mainAmp = 0.5;

freqRatio = ((maxFreq+1)-minFreq)/numCells;
/* freqRatio = Calcula o fator de soma para descobrirmos quais as frequências a serem destribuidas dependendo da quantidade de células que o bitório tem.
Considera a frequência mínima e máxima que determinanos.
*/



freqBittorio = Bittorio01.new(numCells, 45);
ampBittorio = Bittorio01.new(numCells*ampBitSize, 45);
synthBittorio = Array.fill(numCells, {Synth(\synthBittorio, [\gate, 0])}); // Array com um Synth para cada célula

mainRoutine = Routine.new({
	synthBittorio.do({ arg synth;
		synth.set(\gate, 1);
	});
	inf.do({
//		freqBittorio.cells.postln;
	freqBittorio.cells.do({ arg cell, i;
		var amp, freq;

		freq = minFreq+(freqRatio*i)*cell;
			//freq.post; " ".post;
		amp = ampBittorio.groupDecimal(ampBitSize);
			synthBittorio.at(i).set(\freq, freq, \amp, amp.at(i).linlin(0,15,0.0,(1.0/numCells)*mainAmp));


	});
		freqBittorio.calcNextGen;
		ampBittorio.calcNextGen;
		//"".postln;
	0.1.wait;
	});
}).play;
)
