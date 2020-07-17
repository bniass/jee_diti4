<%--
  Created by IntelliJ IDEA.
  User: niass028652
  Date: 26/06/2020
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-dateformat.js"></script>

    <script type="application/javascript">
    $(document).ready(function () {
        $("#service").change(function () {
            if($("#service").val() === ''){
                $('#specialite').empty();
                return;
            }
            $.ajax({
                url:'/rhwebapp/medecin',
                type:"GET",
                dataType:"json",
                data:{action:'findspecialites', service_id:$("#service").val()},
                success:function (data) {
                    console.log(data);
                    var options = "";
                    data.forEach((e) => {
                       options +="<option value='"+e.id+"'>"+e.libelle+"</option>";
                    });
                    $('#specialite').empty();
                    $('#specialite').append(options);
                },
                error:function (err) {
                    console.log(err);
                }
            })
        })

        function get($, bouton){
            idemp = $(bouton).attr('data-target-id');
            $.ajax({
                url:'/rhwebapp/medecin',
                type:"GET",
                dataType:"json",
                data:{action:'findemploye', emp_id:idemp},
                success:function (data) {
                    var tabSpec = []
                    data.specialites.forEach(e => {
                        console.log(e)
                        tabSpec.push(e.id)
                    });
                    var options = ''
                    data.service.specialites.forEach(e => {
                        options +="<option value='"+e.id+"'>"+e.libelle+"</option>";
                });
                    console.log(tabSpec);
                    $('#prenom').val(data.prenom);
                    $('#nom').val(data.nom);
                    $('#tel').val(data.tel);
                    $('#adresse').val(data.adresse);
                    $('#email').val(data.email);
                    var date = "\/Date("+data.datenaissance+")\/";
                    console.log(date);
                    var nowDate = new Date(parseInt(date.substr(6)))
                    console.log(nowDate)
                    //$.format.date(nowDate, 'yyyy-mm-dd')
                    //var result = nowDate.toString("yyyy-mm-dd")
                    $('#datenais').val($.format.date(nowDate, 'yyyy-MM-dd'));
                    $('#service').val(data.service.id);
                    //$('#service').attr('readonly', true);
                    $('#specialite').empty();
                    $('#specialite').append(options);
                    $('#specialite').val(tabSpec);
                    $('#medecinid').val(data.id);
                },
                error:function (err) {
                    console.log(err);
                }
            })
        }
        //-------------------------------------------------
        $('.edit').click(function () {
            get($, $(this));
            $('.masquer').hide();
            $('#action').val('update');
            $('#prenom').removeAttr('readonly');
            $('#nom').removeAttr('readonly');
            $('#tel').removeAttr('readonly');
            $('#adresse').removeAttr('readonly');
            $('#email').removeAttr('readonly');
            $('#specialite').removeAttr('readonly');
            $('#datenais').removeAttr('readonly');
            $('#service').removeAttr('readonly');
        })
        //-------------------------------------------------
        $('.transfert').click(function () {
            get($, $(this));
            $('.masquer').show();
            $('#prenom').attr('readonly', true);
            $('#action').val('transfert');
            $('#nom').attr('readonly', true);
            $('#tel').attr('readonly', true);
            $('#adresse').attr('readonly', true);
            $('#email').attr('readonly', true);
            $('#specialite').attr('readonly', true);
            $('#datenais').attr('readonly', true);
        })
        //-------------------------------------------------
        $('.manage').click(function () {
            get($, $(this));
            $('.masquer').show();
            $('#prenom').attr('readonly', true);
            $('#action').val('managespecialite');
            $('#nom').attr('readonly', true);
            $('#tel').attr('readonly', true);
            $('#adresse').attr('readonly', true);
            $('#email').attr('readonly', true);
            $('#service').attr('readonly', true);
            $('#specialite').removeAttr('readonly');
            $('#datenais').attr('readonly', true);
        })
    });

</script>
</head>
<body>
<div class="container">
    <br/>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">NOUVEAU</button>
    <br/><br/>
    <h2>Liste des medecins</h2>
    <table class="table table-bordered table-sm">
        <thead>
        <tr>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Tel</th>
            <th>Adresse</th>
            <th>Email</th>
            <th>Date de Naissance</th>
            <th>Service</th>
            <th>Update</th>
            <th>Transferer</th>
            <th>Manage specilaites</th>
            <th>Supprimer</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${medecins}" var="medecin">
            <tr>
                <td>${medecin.prenom}</td>
                <td>${medecin.nom}</td>
                <td>${medecin.tel}</td>
                <td>${medecin.adresse}</td>
                <td>${medecin.email}</td>
                <td><fmt:formatDate value="${medecin.datenaissance}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                <td>${medecin.service.libelle}</td>
                <td><a data-target-id="${medecin.id}"
                       class="btn btn-primary edit" href="#"
                       role="button" data-toggle="modal" data-target="#exampleModal">Update</a></td>
                <td><a data-target-id="${medecin.id}"
                       class="btn btn-primary transfert" href="#"
                       role="button" data-toggle="modal" data-target="#exampleModal">Transferer</a></td>

                <td><a data-target-id="${medecin.id}"
                       class="btn btn-primary manage" href="#"
                       role="button" data-toggle="modal" data-target="#exampleModal">specialite</a></td>

                <td><a data-target-id="${medecin.id}"
                       class="btn btn-danger remove" href="#" id="remove"
                       role="button" data-toggle="modal" data-target="#exampleModal">Supprimer</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">New message</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="form1" method="post" action="${pageContext.request.contextPath}/medecin">
            <div class="modal-body">

                    <div class="form-group">
                        <label for="prenom" class="col-form-label">Prénom:</label>
                        <input type="text" name="prenom" class="form-control" id="prenom">
                    </div>
                    <div class="form-group">
                        <label for="nom" class="col-form-label">Nom:</label>
                        <input type="text" name="nom" class="form-control" id="nom">
                    </div>
                    <div class="form-group">
                        <label for="tel" class="col-form-label">Téléphone:</label>
                        <input type="text" name="tel" class="form-control" id="tel">
                    </div>
                    <div class="form-group">
                        <label for="adresse" class="col-form-label">Adresse:</label>
                        <input type="text" name="adresse" class="form-control" id="adresse">
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-form-label">Email:</label>
                        <input type="text" name="email" class="form-control" id="email">
                    </div>
                    <div class="form-group">
                        <label for="datenais" class="col-form-label">Date de naissance:</label>
                        <input type="date" name="datenais" class="form-control" id="datenais">
                    </div>
                    <div class="form-group masquer">
                        <label for="service" class="col-form-label">Service:</label>
                        <select required class="form-control" name="service" id="service">
                            <option value="">---------------</option>
                            <c:forEach items="${services}" var="service">
                                <option value="${service.id}">${service.libelle}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group masquer">
                        <label for="specialite" class="col-form-label">Specialite:</label>
                        <select multiple class="form-control" name="specialite" id="specialite">
                            <option>---------------</option>
                        </select>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Enregister</button>
            </div>
                <input type="text" id="action" name="action" value="add">
                <input type="text" id="medecinid" name="medecinid">
            </form>
        </div>
    </div>
</div>
</body>
</html>
