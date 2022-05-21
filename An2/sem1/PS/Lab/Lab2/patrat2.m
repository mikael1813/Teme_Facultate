function probabilitate = patrat2(N)
  
  clf;rectangle('Position',[0 0 1 1]);axis square;hold on;
  
  puncte=0;
  for i=1:N
    x=rand;
    y=rand;
    if pdist([x y;0.5 0.5]) < pdist([x y;0 1]) && ...
      pdist([x y;0.5 0.5]) < pdist([x y;0 0]) && ...
      pdist([x y;0.5 0.5]) < pdist([x y;1 1]) && ...
      pdist([x y;0.5 0.5]) < pdist([x y;1 0])
      puncte++;
      plot(x,y,'or','MarkerSize',10,'MarkerFaceColor','r');
    end
    
    
  end  
  
  probabilitate = puncte/N;
  
endfunction
