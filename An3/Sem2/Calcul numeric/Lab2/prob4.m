function prob4(x)
  y = num2hex(x);
  y = hex2dec(y);
  y = dec2bin(y);
  count = numel(num2str(y));
  nr = num2str(y);
  while(count<64)
    nr = strcat("0",nr);
    count = count + 1;
  endwhile
  semn = nr(1)
  exponent_deplasat = nr(2:12)
  semnificant = nr(13:end)
endfunction
