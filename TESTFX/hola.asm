.model small
.stack
.data
	v1 db "hola mundo ico 23 $"
.code
	mov ax,@data
	mov ds, ax

	; imprimir una cadena de carecteres

	mov ah,09h
	mov dx, offset v1  ;lea dx,v1
	int 21h

	mov ah,4ch
	int 21h


end
; fin del codigo
; fin de codigo otra vez xD
	 	 	 	