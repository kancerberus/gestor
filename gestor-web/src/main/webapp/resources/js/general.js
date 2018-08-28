function trim(sString) {
    while (sString.substring(0, 1) == ' ')
        sString = sString.substring(1, sString.length);
    while (sString.substring(sString.length - 1, sString.length) == ' ')
        sString = sString.substring(0, sString.length - 1);
    return sString;
}

function validarEntero(valor) {
    if (!/^([0-9])*$/.test(valor))
        return false;
    else
        return true;
}

function validarTelefono(valor) {
    if (valor.length < 7)
        return false;
    if (!/^([0-9\s])*$/.test(valor))
        return false;
    return true;
}

function validarIdentificacion(valor) {
    if (!/^[a-zA-Z0-9\-]*$/.test(valor))
        return false;
    else
        return true;
}

function validarCuentaBanco(valor) {
    if (!/^[0-9\-]+$/.test(valor))
        return false;
    else
        return true;
}

function validarNombreArchivo(valor) {
    if (!/^[a-zA-Z0-9\-\.\_]+$/.test(valor))
        return false;
    else
        return true;
}

function validarCorreo(direccionCorreo) {
    if (/^[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}$/.test(direccionCorreo))
        return true;
    else
        return false;
}

function llamarVentana(pagina, ancho, alto) {
    window.open(pagina, 'Consultar', 'left=' + (screen.availWidth - ancho) / 2 + ',top=' + (screen.availHeight - alto) / 2 + ',width=' + ancho + ',height=' + alto);
}

function ejecutar() {
    var cadena = new String(get('ejecutar').value);
    if (cadena != '') {
        try {
            eval(cadena.toString());
        } catch (e) {
            alert(e.toString());
        }
    }
    get('ejecutar').value = '';
}

function getParametro(mapa, clave) {
    mapa = mapa.replace('{', '');
    mapa = mapa.replace('}', '');
    var parejas = mapa.split(',');
    var valor = '';
    for (i = 0; i < parejas.length; i++) {
        if (Trim(parejas[i].split('=')[0]) == clave) {
            valor = parejas[i].split('=')[1];
            break;
        }
    }
    return valor;
}

function siguiente(field) { //Foco al siguiente campo activo
    var i;
    var bandera = true;
    for (i = 0; i < field.form.elements.length; i++)
        if (field == field.form.elements[i])
            break;
    while (bandera) {
        i = (i + 1) % field.form.elements.length;
        if (field.form.elements[i].disabled == false && field.form.elements[i].readOnly != true && field.form.elements[i].type != 'hidden') {
            bandera = false;
        }
    }
    field.form.elements[i].focus();
    return false;
}

function hola() {
    alert('hola desde general');
}

function mensajes(data) {
    var respuestaSi;
    var respuestaNo;
    alert(data);
    for (var i = 0; i < data.length; i++) {
        alert(data[i].tipo);
        if (data[i].tipo === 'alert') {
            alert(data[i].descripcion);
        } else if (data[i].tipo === 'confirm') {
            if (confirm(data[i].descripcion)) {
                try {
                    if (data[i].parametros.length > 0) {
                        eval(data[i].respuestaSi + "('" + data[i].parametros + "')");
                    } else {
                        eval(data[i].respuestaSi);
                    }
                } catch (E) {
                    alert(E);
                }
            } else {
                try {
                    if (data[i].parametros.length > 0) {
                        eval(data[i].respuestaNo + "('" + data[i].parametros + "')");
                    } else {
                        eval(data[i].respuestaNo);
                    }
                } catch (E) {
                    alert(E);
                }
            }
        } else if (data[i].tipo === 'windowopen') {
            data[i].ruta = replaceAll(data[i].ruta, '%', '');
            window.open(data[i].ruta, data[i].titulo, "'left='+(screen.availWidth-" + data[i].ancho + ")/2',top='+(screen.availHeight-"
                    + data[i].alto + ")/2+',scrollbars=" + data[i].scrollbars + ",resizable=" + data[i].rezisable + ",width=" + data[i].ancho + ",height=" + data[i].alto + "'");
        } else if (data[i].tipo === 'modal') {
            Richfaces.showModalPanel(data[i].descripcion);
        } else if (data[i].tipo === 'hide-modal') {
            Richfaces.hideModalPanel(data[i].descripcion);
        } else if (data[i].tipo === 'data') {
            eval(data[i].descripcion);
        } else if (data[i].tipo === 'jalert') {
            jAlert(data[i].descripcion, data[i].titulo);
        } else if (data[i].tipo === 'jmensaje') {
            var respuesta = data[i].respuestaSi;
            if (respuesta === null) {
                jMensaje(data[i].descripcion, data[i].titulo, data[i].etiquetaSi);
            } else {
                mensajeJmensaje(data[i].descripcion, data[i].titulo, data[i].etiquetaSi, respuesta);
            }
        } else if (data[i].tipo === 'jpregunta') {
            respuestaSi = data[i].respuestaSi;
            respuestaNo = data[i].respuestaNo;
            mensajeJpregunta(data[i].descripcion, data[i].titulo, data[i].etiquetaSi, data[i].etiquetaNo, respuestaSi, respuestaNo);
        } else if (data[i].tipo === 'jseleccion') {
            respuestaSi = data[i].respuestaSi;
            respuestaNo = data[i].respuestaNo;
            jSeleccion(data[i].descripcion, data[i].titulo, data[i].etiquetaSi, data[i].etiquetaNo, function (r) {
                if (r) {
                    eval(respuestaSi);
                } else {
                    eval(respuestaNo);
                }
            });
        }
    }
}

/**
 * permite reemplazar el comentario de los botones de un confirm y una mejor apariencia.
 * jMensaje('Descripcion','Titulo','LabelSI','LabelNO',function(r){if(r){...}else{...}});
 * Si es necesario modificar el estilo se debe crear un nuevo .css, tratalo de generarlizarlo
 * Para usarlo solo agrege en el .jsp
 * 
 *<link rel="stylesheet" href="../estilos/jalert.css"/>
 *<script type="text/javascript" src="./../scripts/jquery-ui-1.8.custom/js/jquery.alerts.js"></script>
 *@param {String} descripcion
 *@param {String} titulo
 *@param {String} etiquetaSi
 *@param {String} etiquetaNo
 *@param {String} respuestaSi hace referencia a la acción que se va a realizar en el evento del boton.
 *@param {String} respuestaNo hace referencia a la acción que se va a realizar en el evento del boton.
 * 
 */
function mensajeJpregunta(descripcion, titulo, etiquetaSi, etiquetaNo, respuestaSi, respuestaNo) {
    jPregunta(descripcion, titulo, etiquetaSi, etiquetaNo, function (r) {
        if (r) {
            eval(respuestaSi);
        } else {
            eval(respuestaNo);
        }
    });
}

/*
 * permite reemplazar el comentario de los botones de un alert y una mejor apariencia.
 * jMensaje('Descripcion','Titulo','LabelSI');
 * Si es necesario modificar el estilo se debe crear un nuevo .css, tratalo de generarlizarlo
 * Para usarlo solo agrege en el .jsp
 * 
 *<link rel="stylesheet" href="../estilos/jalert.css"/>
 *<script type="text/javascript" src="./../scripts/jquery-ui-1.8.custom/js/jquery.alerts.js"></script>
 *
 *@param {String} descripcion
 *@param {String} titulo
 *@param {String} etiquetaSi
 *@param {String} respuesta hace referencia a la acción que se va a realizar en el evento del boton.
 *
 **/
function mensajeJmensaje(descripcion, titulo, etiquetaSi, respuesta) {
    jMensaje(descripcion, titulo, etiquetaSi, function (r) {
        if (r) {
            eval(respuesta);
        }
    });
}

function get(id) {
    if (document.getElementById(id) != null)
        return document.getElementById(id);
    for (i = document.forms.length - 1; i >= 0; i--) {
        if (document.getElementById(document.forms[i].id + ':' + id) != null) {
            return document.getElementById(document.forms[i].id + ':' + id);
        }
    }
    return document.getElementById(id);
}

function processObjectsGeneral(suggestionBox, objetoTexto, parametroAsignar) {
    var currentValue = suggestionBox.getValue();
    var items = suggestionBox.items;
    if (items && items.length > 0) {
        var item = jQuery(items).find("span[data-value='" + currentValue + "']");
        if (jQuery(item).attr('data-uuid')) {
            get(objetoTexto).value = jQuery(item).attr('data-uuid');
        } else {
            get(objetoTexto).value = '';
        }
        get(objetoTexto).focus();
    } else
        get(objetoTexto).value = '';
}

function enter(event) {
    return event == '13';
}