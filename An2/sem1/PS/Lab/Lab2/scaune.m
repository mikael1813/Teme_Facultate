function numar=scaune(m,n,N)
  
  fete = repmat('f',1,m);
  baieti = repmat('b',1,n);
  
  grup = ['X',fete,baieti];
  
  numar = 0;
  
  for i=1:N
    bilete=randperm(length(grup));
    asezare = grup(bilete);
    for j=2:m+n
      if(asezare(j-1) == 'f' && asezare(j) == 'X' && asezare(j+1) == 'f')
        numar = numar + 1;
        break;
      end
    end
  end
  
  numar = numar/N;
  
  probabilitati = m*(m-1)/((m+n+1)*(m+n))
