clear all;
close all;
clc;

Fs = 48000;
Ts = 1/48000;

[b,a] = butter(2,150*2/Fs,'low');
[b1,a1] = butter(2,150*2/Fs,'high');

bs = round(b./a(3).*2^19)  %coef for lpf
as = round(a./a(3) .*2^19)

bs1 = round(b1./a1(3).*2^19) %coef for hpf
as1 = round(a1./a1(3).*2^19)

hold on;
[h,w] = freqz(b,a,1024);
[h1,w1] = freqz(b1,a1,1024);
[hs,ws] = freqz(bs,as,1024);
[hs1,ws1] = freqz(bs1,as1,1024);

plot(w*Fs/pi/2,mag2db(abs(h)), ws*Fs/pi/2,mag2db(abs(hs)))
plot(w1*Fs/pi/2,mag2db(abs(h1)), ws1*Fs/pi/2,mag2db(abs(hs1)))
legend('butter Design', 'scaled')
xlabel 'Radian Frequency (\omega/\pi)', ylabel 'Magnitude'
xlim([0 0.1*10^4]);
ylim([-30 1]);